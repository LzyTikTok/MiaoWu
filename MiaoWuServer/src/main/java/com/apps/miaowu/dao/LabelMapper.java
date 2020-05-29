package com.apps.miaowu.dao;

import com.apps.miaowu.bean.Label;
import com.apps.miaowu.bean.LabelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LabelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label
     *
     * @mbg.generated
     */
    long countByExample(LabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label
     *
     * @mbg.generated
     */
    int deleteByExample(LabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label
     *
     * @mbg.generated
     */
    int insert(Label record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label
     *
     * @mbg.generated
     */
    int insertSelective(Label record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label
     *
     * @mbg.generated
     */
    List<Label> selectByExample(LabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label
     *
     * @mbg.generated
     */
    Label selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Label record, @Param("example") LabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Label record, @Param("example") LabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Label record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table label
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Label record);
}