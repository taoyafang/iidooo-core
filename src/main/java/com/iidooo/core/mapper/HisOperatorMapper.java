package com.iidooo.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iidooo.core.model.po.HisOperator;

public interface HisOperatorMapper {
    int deleteByPrimaryKey(Integer operatorID);

    /**
     * 插入一个HisOperator数据进入数据库
     * @param hisOperator 该操作历史对象会被插入数据库
     * @return 成功与否所返回的行数
     */
    int insert(HisOperator hisOperator);

    /**
     * 得到PV统计
     * @param hisOperator 包含PV统计所需要的一些值
     * @return 所统计到的PV
     */
    int selectPVCount(HisOperator hisOperator);
    
    /**
     * 得到UV统计
     * @param hisOperator 包含UV统计所需要的一些值
     * @return 所统计到的UV
     */
    int selectUVCount(@Param("hisOperator")HisOperator hisOperator, @Param("optionList")List<String> optionList);     

    int updateByPrimaryKeySelective(HisOperator record);

    int updateByPrimaryKey(HisOperator record);
}