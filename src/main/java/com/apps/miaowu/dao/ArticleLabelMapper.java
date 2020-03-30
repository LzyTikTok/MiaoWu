package com.apps.miaowu.dao;

import com.apps.miaowu.bean.ArticleLabel;
import com.apps.miaowu.bean.ArticleLabelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleLabelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    long countByExample(ArticleLabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    int deleteByExample(ArticleLabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    int insert(ArticleLabel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    int insertSelective(ArticleLabel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    List<ArticleLabel> selectByExample(ArticleLabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    ArticleLabel selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    int updateByExampleSelective(@Param("record") ArticleLabel record, @Param("example") ArticleLabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    int updateByExample(@Param("record") ArticleLabel record, @Param("example") ArticleLabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    int updateByPrimaryKeySelective(ArticleLabel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    int updateByPrimaryKey(ArticleLabel record);
}