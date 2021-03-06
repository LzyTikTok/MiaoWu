package com.apps.miaowu.dao;

import com.apps.miaowu.bean.Animal;
import com.apps.miaowu.bean.AnimalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AnimalMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated
     */
    long countByExample(AnimalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated
     */
    int deleteByExample(AnimalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated
     */
    int insert(Animal record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated
     */
    int insertSelective(Animal record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated
     */
    List<Animal> selectByExample(AnimalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated
     */
    Animal selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Animal record, @Param("example") AnimalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Animal record, @Param("example") AnimalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Animal record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Animal record);
}