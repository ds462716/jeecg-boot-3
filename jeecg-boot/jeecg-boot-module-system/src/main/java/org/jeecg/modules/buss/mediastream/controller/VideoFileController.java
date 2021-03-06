package org.jeecg.modules.buss.mediastream.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.buss.mediastream.entity.VideoFile;
import org.jeecg.modules.buss.mediastream.service.IVideoFileService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: tb_video_file
 * @Author: jeecg-boot
 * @Date:   2020-03-25
 * @Version: V1.0
 */
@Api(tags="tb_video_file")
@RestController
@RequestMapping("/mediastream/videoFile")
@Slf4j
public class VideoFileController extends JeecgController<VideoFile, IVideoFileService> {
	@Autowired
	private IVideoFileService videoFileService;
	
	/**
	 * 分页列表查询
	 *
	 * @param videoFile
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "tb_video_file-分页列表查询")
	@ApiOperation(value="tb_video_file-分页列表查询", notes="tb_video_file-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(VideoFile videoFile,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<VideoFile> queryWrapper = QueryGenerator.initQueryWrapper(videoFile, req.getParameterMap());
		Page<VideoFile> page = new Page<VideoFile>(pageNo, pageSize);
		IPage<VideoFile> pageList = videoFileService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param videoFile
	 * @return
	 */
	@AutoLog(value = "tb_video_file-添加")
	@ApiOperation(value="tb_video_file-添加", notes="tb_video_file-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody VideoFile videoFile) {
		videoFileService.save(videoFile);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param videoFile
	 * @return
	 */
	@AutoLog(value = "tb_video_file-编辑")
	@ApiOperation(value="tb_video_file-编辑", notes="tb_video_file-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody VideoFile videoFile) {
		videoFileService.updateById(videoFile);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tb_video_file-通过id删除")
	@ApiOperation(value="tb_video_file-通过id删除", notes="tb_video_file-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		videoFileService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "tb_video_file-批量删除")
	@ApiOperation(value="tb_video_file-批量删除", notes="tb_video_file-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.videoFileService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tb_video_file-通过id查询")
	@ApiOperation(value="tb_video_file-通过id查询", notes="tb_video_file-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		VideoFile videoFile = videoFileService.getById(id);
		if(videoFile==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(videoFile);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param videoFile
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, VideoFile videoFile) {
        return super.exportXls(request, videoFile, VideoFile.class, "tb_video_file");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, VideoFile.class);
    }

}
