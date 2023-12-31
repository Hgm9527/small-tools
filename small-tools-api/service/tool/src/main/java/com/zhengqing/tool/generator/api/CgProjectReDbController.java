package com.zhengqing.tool.generator.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.tool.generator.model.dto.CgProjectReDbListDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectReDbSaveDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectReDbTableInfoDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectReDbTableListDTO;
import com.zhengqing.tool.generator.model.vo.CgProjectReDbListVO;
import com.zhengqing.tool.generator.model.vo.CgTableInfoVO;
import com.zhengqing.tool.generator.model.vo.CgTableListVO;
import com.zhengqing.tool.generator.service.ICgProjectReDbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 代码生成器 - 项目关联数据库表 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-11-14 13:55:47
 */
@RestController
@RequestMapping("/web/api/generator/projectReDb")
@Api(tags = {"代码生成器 - 项目关联数据库表接口"})
public class CgProjectReDbController extends BaseController {

    @Resource
    private ICgProjectReDbService codeProjectReDbService;

    @GetMapping("listPage")
    @ApiOperation("列表分页")
    public IPage<CgProjectReDbListVO> listPage(@ModelAttribute CgProjectReDbListDTO params) {
        return this.codeProjectReDbService.listPage(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<CgProjectReDbListVO> list(@ModelAttribute CgProjectReDbListDTO params) {
        return this.codeProjectReDbService.list(params);
    }

    @GetMapping("tableList")
    @ApiOperation("表列表信息")
    public List<CgTableListVO> tableList(@ModelAttribute CgProjectReDbTableListDTO params) {
        return this.codeProjectReDbService.tableList(params);
    }

    @GetMapping("tableInfo")
    @ApiOperation("表具体信息（含表字段列表）")
    public CgTableInfoVO tableInfo(@ModelAttribute CgProjectReDbTableInfoDTO params) {
        return this.codeProjectReDbService.tableInfo(params);
    }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody CgProjectReDbSaveDTO params) {
        return this.codeProjectReDbService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody CgProjectReDbSaveDTO params) {
        return this.codeProjectReDbService.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer projectReDbDataSourceId) {
        this.codeProjectReDbService.removeById(projectReDbDataSourceId);
    }

}
