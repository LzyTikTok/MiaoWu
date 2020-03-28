package com.apps.miaowu.dao;

import com.apps.miaowu.bean.Found;
import com.apps.miaowu.bean.FoundExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FoundMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    long countByExample(FoundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    int deleteByExample(FoundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    int insert(Found record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    int insertSelective(Found record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    List<Found> selectByExample(FoundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    Found selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    int updateByExampleSelective(@Param("record") Found record, @Param("example") FoundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    int updateByExample(@Param("record") Found record, @Param("example") FoundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    int updateByPrimaryKeySelective(Found record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    int updateByPrimaryKey(Found record);
}