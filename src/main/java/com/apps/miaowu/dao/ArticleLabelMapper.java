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
     * @mbg.generated
     */
    long countByExample(ArticleLabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated
     */
    int deleteByExample(ArticleLabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated
     */
    int insert(ArticleLabel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated
     */
    int insertSelective(ArticleLabel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated
     */
    List<ArticleLabel> selectByExample(ArticleLabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated
     */
    ArticleLabel selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ArticleLabel record, @Param("example") ArticleLabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ArticleLabel record, @Param("example") ArticleLabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ArticleLabel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_label
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ArticleLabel record);
}