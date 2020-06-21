package com.apps.miaowu.dao;

import com.apps.miaowu.bean.Clip;
import com.apps.miaowu.bean.ClipExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClipMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table clip
     *
     * @mbg.generated
     */
    long countByExample(ClipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table clip
     *
     * @mbg.generated
     */
    int deleteByExample(ClipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table clip
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table clip
     *
     * @mbg.generated
     */
    int insert(Clip record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table clip
     *
     * @mbg.generated
     */
    int insertSelective(Clip record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table clip
     *
     * @mbg.generated
     */
    List<Clip> selectByExample(ClipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table clip
     *
     * @mbg.generated
     */
    Clip selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table clip
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Clip record, @Param("example") ClipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table clip
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Clip record, @Param("example") ClipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table clip
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Clip record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table clip
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Clip record);
}