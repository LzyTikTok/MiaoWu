package com.apps.miaowu.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FoundExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    public FoundExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andAnimalIdIsNull() {
            addCriterion("animal_id is null");
            return (Criteria) this;
        }

        public Criteria andAnimalIdIsNotNull() {
            addCriterion("animal_id is not null");
            return (Criteria) this;
        }

        public Criteria andAnimalIdEqualTo(Long value) {
            addCriterion("animal_id =", value, "animalId");
            return (Criteria) this;
        }

        public Criteria andAnimalIdNotEqualTo(Long value) {
            addCriterion("animal_id <>", value, "animalId");
            return (Criteria) this;
        }

        public Criteria andAnimalIdGreaterThan(Long value) {
            addCriterion("animal_id >", value, "animalId");
            return (Criteria) this;
        }

        public Criteria andAnimalIdGreaterThanOrEqualTo(Long value) {
            addCriterion("animal_id >=", value, "animalId");
            return (Criteria) this;
        }

        public Criteria andAnimalIdLessThan(Long value) {
            addCriterion("animal_id <", value, "animalId");
            return (Criteria) this;
        }

        public Criteria andAnimalIdLessThanOrEqualTo(Long value) {
            addCriterion("animal_id <=", value, "animalId");
            return (Criteria) this;
        }

        public Criteria andAnimalIdIn(List<Long> values) {
            addCriterion("animal_id in", values, "animalId");
            return (Criteria) this;
        }

        public Criteria andAnimalIdNotIn(List<Long> values) {
            addCriterion("animal_id not in", values, "animalId");
            return (Criteria) this;
        }

        public Criteria andAnimalIdBetween(Long value1, Long value2) {
            addCriterion("animal_id between", value1, value2, "animalId");
            return (Criteria) this;
        }

        public Criteria andAnimalIdNotBetween(Long value1, Long value2) {
            addCriterion("animal_id not between", value1, value2, "animalId");
            return (Criteria) this;
        }

        public Criteria andSiteIsNull() {
            addCriterion("site is null");
            return (Criteria) this;
        }

        public Criteria andSiteIsNotNull() {
            addCriterion("site is not null");
            return (Criteria) this;
        }

        public Criteria andSiteEqualTo(String value) {
            addCriterion("site =", value, "site");
            return (Criteria) this;
        }

        public Criteria andSiteNotEqualTo(String value) {
            addCriterion("site <>", value, "site");
            return (Criteria) this;
        }

        public Criteria andSiteGreaterThan(String value) {
            addCriterion("site >", value, "site");
            return (Criteria) this;
        }

        public Criteria andSiteGreaterThanOrEqualTo(String value) {
            addCriterion("site >=", value, "site");
            return (Criteria) this;
        }

        public Criteria andSiteLessThan(String value) {
            addCriterion("site <", value, "site");
            return (Criteria) this;
        }

        public Criteria andSiteLessThanOrEqualTo(String value) {
            addCriterion("site <=", value, "site");
            return (Criteria) this;
        }

        public Criteria andSiteLike(String value) {
            addCriterion("site like", value, "site");
            return (Criteria) this;
        }

        public Criteria andSiteNotLike(String value) {
            addCriterion("site not like", value, "site");
            return (Criteria) this;
        }

        public Criteria andSiteIn(List<String> values) {
            addCriterion("site in", values, "site");
            return (Criteria) this;
        }

        public Criteria andSiteNotIn(List<String> values) {
            addCriterion("site not in", values, "site");
            return (Criteria) this;
        }

        public Criteria andSiteBetween(String value1, String value2) {
            addCriterion("site between", value1, value2, "site");
            return (Criteria) this;
        }

        public Criteria andSiteNotBetween(String value1, String value2) {
            addCriterion("site not between", value1, value2, "site");
            return (Criteria) this;
        }

        public Criteria andFoundDateIsNull() {
            addCriterion("found_date is null");
            return (Criteria) this;
        }

        public Criteria andFoundDateIsNotNull() {
            addCriterion("found_date is not null");
            return (Criteria) this;
        }

        public Criteria andFoundDateEqualTo(Date value) {
            addCriterion("found_date =", value, "foundDate");
            return (Criteria) this;
        }

        public Criteria andFoundDateNotEqualTo(Date value) {
            addCriterion("found_date <>", value, "foundDate");
            return (Criteria) this;
        }

        public Criteria andFoundDateGreaterThan(Date value) {
            addCriterion("found_date >", value, "foundDate");
            return (Criteria) this;
        }

        public Criteria andFoundDateGreaterThanOrEqualTo(Date value) {
            addCriterion("found_date >=", value, "foundDate");
            return (Criteria) this;
        }

        public Criteria andFoundDateLessThan(Date value) {
            addCriterion("found_date <", value, "foundDate");
            return (Criteria) this;
        }

        public Criteria andFoundDateLessThanOrEqualTo(Date value) {
            addCriterion("found_date <=", value, "foundDate");
            return (Criteria) this;
        }

        public Criteria andFoundDateIn(List<Date> values) {
            addCriterion("found_date in", values, "foundDate");
            return (Criteria) this;
        }

        public Criteria andFoundDateNotIn(List<Date> values) {
            addCriterion("found_date not in", values, "foundDate");
            return (Criteria) this;
        }

        public Criteria andFoundDateBetween(Date value1, Date value2) {
            addCriterion("found_date between", value1, value2, "foundDate");
            return (Criteria) this;
        }

        public Criteria andFoundDateNotBetween(Date value1, Date value2) {
            addCriterion("found_date not between", value1, value2, "foundDate");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table found_table
     *
     * @mbg.generated do_not_delete_during_merge Sun Feb 23 20:26:21 CST 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table found_table
     *
     * @mbg.generated Sun Feb 23 20:26:21 CST 2020
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}