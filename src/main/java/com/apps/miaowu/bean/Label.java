package com.apps.miaowu.bean;

public class Label {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column label.id
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column label.name
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    private String name;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column label.id
     *
     * @return the value of label.id
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column label.id
     *
     * @param id the value for label.id
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column label.name
     *
     * @return the value of label.name
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column label.name
     *
     * @param name the value for label.name
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}