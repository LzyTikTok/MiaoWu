package com.apps.miaowu.dao;

import com.apps.miaowu.bean.Save;
import com.apps.miaowu.bean.SaveExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SaveMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table save
     *
     * @mbg.generated
     */
    long countByExample(SaveExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table save
     *
     * @mbg.generated
     */
    int deleteByExample(SaveExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table save
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table save
     *
     * @mbg.generated
     */
    int insert(Save record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table save
     *
     * @mbg.generated
     */
    int insertSelective(Save record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table save
     *
     * @mbg.generated
     */
    List<Save> selectByExample(SaveExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table save
     *
     * @mbg.generated
     */
    Save selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table save
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Save record, @Param("example") SaveExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table save
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Save record, @Param("example") SaveExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table save
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Save record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table save
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Save record);
}