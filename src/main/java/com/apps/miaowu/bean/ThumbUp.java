package com.apps.miaowu.bean;

public class ThumbUp {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column thumb_up.id
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column thumb_up.user_id
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column thumb_up.article_id
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    private Long articleId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column thumb_up.id
     *
     * @return the value of thumb_up.id
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column thumb_up.id
     *
     * @param id the value for thumb_up.id
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column thumb_up.user_id
     *
     * @return the value of thumb_up.user_id
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column thumb_up.user_id
     *
     * @param userId the value for thumb_up.user_id
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column thumb_up.article_id
     *
     * @return the value of thumb_up.article_id
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    public Long getArticleId() {
        return articleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column thumb_up.article_id
     *
     * @param articleId the value for thumb_up.article_id
     *
     * @mbg.generated Mon Mar 30 23:04:18 CST 2020
     */
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
}