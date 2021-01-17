package com.zhengqing.tool.generator.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.tool.generator.entity.CgTableConfig;
import com.zhengqing.tool.generator.model.dto.CgTableConfigListDTO;

/**
 * <p>
 * 项目数据表配置信息表 Mapper 接口
 * </p>
 *
 * @description :
 * @author : zhengqing
 * @date : 2019/8/22 11:14
 */
public interface CgTableConfigMapper extends BaseMapper<CgTableConfig> {

    /**
     * 列表分页
     *
     * @param page
     * @param filter
     * @return
     */
    List<CgTableConfig> selectDataList(IPage page, @Param("filter") CgTableConfigListDTO filter);

    /**
     * 列表
     *
     * @param filter
     * @return
     */
    List<CgTableConfig> selectDataList(@Param("filter") CgTableConfigListDTO filter);

}
