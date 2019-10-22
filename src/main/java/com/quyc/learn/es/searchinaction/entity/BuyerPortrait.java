package com.quyc.learn.es.searchinaction.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 买家
 * Created by ZhouHangqi on 2018/1/10.
 */
@ToString
@Data
public class BuyerPortrait extends BaseModel {

    private String buyerId;

    private String shopId;

    private Long userTags;

    /**
     * 通用扩展字段
     */
    private String features;

    /**
     * 用户标签（按位打标签，标签位放在标签表内）
     */
    private Long labelTag;

    /**
     * 状态 1正常 0删除
     */
    private Integer status;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区县
     */
    private String area;

    /**
     * 性别
     * 1：男；2：女；3：未知
     */
    private Integer sex;

    /**
     * 付款总金额-全部 （单位分）
     */
    private Long orderPayed;

    /**
     * 付款总单量-全部（单位单）
     */
    private Long orderCount;

    /**
     * 客单价 全部（单位 分）
     */
    private Long averageProduct;

    /**
     * 付款件数 全部（单位 件）
     */
    private Long productCount;

    /**
     * 付款总金额-30天 （单位分）
     */
    private Long orderPayed30;

    /**
     * 付款总单量-30天（单位单）
     */
    private Long orderCount30;

    /**
     * 客单价 30天（单位 分）
     */
    private Long averageProduct30;

    /**
     * 付款件数 30天（单位 件）
     */
    private Long productCount30;

    /**
     * 付款总金额-60天 （单位分）
     */
    private Long orderPayed60;

    /**
     * 付款总单量-60天（单位单）
     */
    private Long orderCount60;

    /**
     * 客单价 60天（单位 分）
     */
    private Long averageProduct60;

    /**
     * 付款件数 60天（单位 件）
     */
    private Long productCount60;

    /**
     * 付款总金额-90天 （单位分）
     */
    private Long orderPayed90;

    /**
     * 付款总单量-90天（单位单）
     */
    private Long orderCount90;

    /**
     * 客单价 90天（单位 分）
     */
    private Long averageProduct90;

    /**
     * 付款件数 90天（单位 件）
     */
    private Long productCount90;


    /**
     * 付款总金额-180天 （单位分）
     */
    private Long orderPayed180;

    /**
     * 付款总单量-180天（单位单）
     */
    private Long orderCount180;

    /**
     * 客单价 180天（单位 分）
     */
    private Long averageProduct180;

    /**
     * 付款件数 180天（单位 件）
     */
    private Long productCount180;

    /**
     * 订单状态、咨询tag（有无下单、有无付款、有无咨询；分30天 60天 90天 180天 历史）
     */
    private Long orderConsultTag;

    /**
     * 最近咨询时间
     */
    private Date gmtLastConsultation;

    /**
     * 最近付款时间
     */
    private Date gmtLastPayed;
    /**
     * 手机号
     */
    private String phone;
}
