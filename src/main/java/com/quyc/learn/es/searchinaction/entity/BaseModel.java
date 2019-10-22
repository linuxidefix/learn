package com.quyc.learn.es.searchinaction.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wugang on 2017/2/23.
 */
public class BaseModel implements Serializable {
    /**
     * 主键
     */


    private Long id;
    /**
     * 是否已删除, 0:未删除, 1
     */
    private Short isDeleted;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 更新时间
     */
    private Date updatedAt;
//    /**
////     * 分页对象
////     */
////    private Pager pager;
    /**
     * 开始时间
     */
    private String startDate;
    /**
     * 结束时间
     */
    private String endDate;


    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

//    public Pager getPager() {
//        return pager;
//    }

//    public void setPager(Pager pager) {
//        this.pager = pager;
//    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * 初始化创建参数
     */
    public void initCreate() {
        this.isDeleted = 0;
        this.updatedAt = this.createdAt = new Date();
    }

    /**
     * 初始化更新参数
     */
    public void initUpdate() {
        this.updatedAt = new Date();
    }

    /**
     * 初始化删除参数
     */
    public void initDelete() {
        this.isDeleted = 1;
        this.updatedAt = new Date();
    }
}
