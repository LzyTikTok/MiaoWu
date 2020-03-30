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
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    long countByExample(AnimalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    int deleteByExample(AnimalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    int insert(Animal record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    int insertSelective(Animal record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    List<Animal> selectByExample(AnimalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    Animal selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    int updateByExampleSelective(@Param("record") Animal record, @Param("example") AnimalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    int updateByExample(@Param("record") Animal record, @Param("example") AnimalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    int updateByPrimaryKeySelective(Animal record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table animal
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    int updateByPrimaryKey(Animal record);
}