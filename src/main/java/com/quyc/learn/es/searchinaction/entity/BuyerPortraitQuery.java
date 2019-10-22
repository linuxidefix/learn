package com.quyc.learn.es.searchinaction.entity;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 描述
 * 用户画像查询条件
 *
 * @author Created by mayu
 * @create 2019-01-2019/1/12
 */
@Data
@ToString
public class BuyerPortraitQuery {


    private static final long serialVersionUID = -3851967833677366016L;

    /**
     * 是否有Es的should条件
     */
    private boolean hasShould;
    /**
     * 任务Id
     */
    private String taskId;

    /**
     * 是否短信
     */
    private Boolean smsStatus;

    /**
     * 买家画像id
     */
    private String id;
    /**
     * 店铺id
     */
    private String shopId;

    /**
     * 买家id
     */
    private String buyerId;

    /**
     * 最近付款时间-开始时间
     */
    private Date startPayedTime;

    /**
     * 最近付款时间-结束时间
     */
    private Date endPayedTime;

    /**
     * 最近咨询时间-开始时间
     */
    private Date startConsulTime;

    /**
     * 最近咨询时间-结束时间
     */
    private Date endConsulTime;

    /**
     * 性别（1：男；2：女；3：未知）
     */
    private Integer sex;

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
     * 付款金额-最小值
     */
    private Long minOrderPayed;

    /**
     * 付款金额-最大值
     */
    private Long maxOrderPayed;

    /**
     * 付款笔数-最小值
     */
    private Integer minOrderCount;

    /**
     * 付款笔数-最大值
     */
    private Integer maxOrderCount;

    /**
     * 客单价-最小值
     */
    private Long minAverageProduct;

    /**
     * 客单价-最大值
     */
    private Long maxAverageProduct;

    /**
     * 付款件数-最小值
     */
    private Integer minProductCount;

    /**
     * 付款件数-最大值
     */
    private Integer maxProductCount;

    /**
     * 接待模式 1：人工接待 0：自动接待
     */
    private Long blackEnable;

    /**
     * 排序字段
     */
    private String sortColumn;

    /**
     * 排序规则
     */
    private String sortOrder;

    /**
     * 买家画像 标签tag（手动标签位）
     */
    private Long labelTag;

    /**
     * 标签id的集合
     */
    private List<String> labelIds;

    /**
     * 地域（省市县 按顺序排列）
     */
    private List<String> region;

    /**
     * 页码。为了兼容以前的字段
     */
    private Integer pageIndex;

    /**
     * 原有查询条件
     */
    private OriginalCondition originalCondition;

    //=================================标签条件转换==============================

    /**
     * 排序的sql 根据页面传过来的数据组装
     */
    private String orderSql;

    /**
     * 标签的匹配条件
     */
    private Integer matchRule;

    /**
     * 咨询订单tag
     */
    private Long orderConsultTag;

    /**
     * 咨询订单  ！tag  逻辑
     */
    private Long nonOrderConsultTag;

    /**
     * 省交集
     */
    private List<String> provinces = Lists.newArrayList();

    /**
     * 市交集
     */
    private List<String> citys = Lists.newArrayList();

    /**
     * 区县交集
     */
    private List<String> areas = Lists.newArrayList();

    /**
     * 性别交集
     */
    private List<Integer> sexs = Lists.newArrayList();


    /**
     * 最后咨询时间--开始时间
     */
    private Integer lastConsultStartDays;
    /**
     * 最后咨询时间--结束时间
     */
    private Integer lastConsultEndDays;


    /**
     * 最后付款时间--开始时间
     */
    private Integer lastPayedStartDays;
    /**
     * 最后付款时间--结束时间
     */
    private Integer lastPayedEndDays;


    /**
     * 30天付款金额-最小值
     */
    private Long minOrderPayed30;

    /**
     * 30天付款金额-最大值
     */
    private Long maxOrderPayed30;

    /**
     * 60天付款金额-最小值
     */
    private Long minOrderPayed60;

    /**
     * 60天付款金额-最大值
     */
    private Long maxOrderPayed60;

    /**
     * 90天付款金额-最小值
     */
    private Long minOrderPayed90;

    /**
     * 90天付款金额-最大值
     */
    private Long maxOrderPayed90;

    /**
     * 180天付款金额-最小值
     */
    private Long minOrderPayed180;

    /**
     * 180天付款金额-最大值
     */
    private Long maxOrderPayed180;


    /**
     * 30客单价-最小值
     */
    private Long minAverageProduct30;

    /**
     * 30客单价-最大值
     */
    private Long maxAverageProduct30;

    /**
     * 60客单价-最小值
     */
    private Long minAverageProduct60;

    /**
     * 60客单价-最大值
     */
    private Long maxAverageProduct60;

    /**
     * 90客单价-最小值
     */
    private Long minAverageProduct90;

    /**
     * 90客单价-最大值
     */
    private Long maxAverageProduct90;

    /**
     * 180客单价-最小值
     */
    private Long minAverageProduct180;

    /**
     * 180客单价-最大值
     */
    private Long maxAverageProduct180;


    /**
     * 30付款笔数-最小值
     */
    private Integer minOrderCount30;

    /**
     * 30付款笔数-最大值
     */
    private Integer maxOrderCount30;

    /**
     * 60付款笔数-最小值
     */
    private Integer minOrderCount60;

    /**
     * 60付款笔数-最大值
     */
    private Integer maxOrderCount60;

    /**
     * 90付款笔数-最小值
     */
    private Integer minOrderCount90;

    /**
     * 90付款笔数-最大值
     */
    private Integer maxOrderCount90;

    /**
     * 180付款笔数-最小值
     */
    private Integer minOrderCount180;

    /**
     * 180付款笔数-最大值
     */
    private Integer maxOrderCount180;


    /**
     * 30付款件数-最小值
     */
    private Integer minProductCount30;

    /**
     * 30付款件数-最大值
     */
    private Integer maxProductCount30;


    /**
     * 60付款件数-最小值
     */
    private Integer minProductCount60;

    /**
     * 60付款件数-最大值
     */
    private Integer maxProductCount60;

    /**
     * 90付款件数-最小值
     */
    private Integer minProductCount90;

    /**
     * 90付款件数-最大值
     */
    private Integer maxProductCount90;

    /**
     * 180付款件数-最小值
     */
    private Integer minProductCount180;

    /**
     * 180付款件数-最大值
     */
    private Integer maxProductCount180;

    /**
     * true 30天内有发过消息
     */
    private Boolean haveCommunication;

    /**
     * true 28天未发消息或未创建订单
     */
    private Boolean noCommunication;
    /**
     * 7天有咨询
     */
    private Long consult7;
    /**
     * 15天有咨询
     */
    private Long consult15;
    /**
     * 30天有咨询
     */
    private Long consult30;
    /**
     * all 有咨询
     */
    private Long consultAll;
    /**
     * 7天未下单
     */
    private Long unOrder7;
    /**
     * 15天未下单
     */
    private Long unOrder15;
    /**
     * 30天未下单
     */
    private Long unOrder30;
    /**
     * all 未下单
     */
    private Long unOrderAll;
    /**
     * 30天有付款
     */
    private Long payed30;
    /**
     * 60天有付款
     */
    private Long payed60;
    /**
     * 18天有付款
     */
    private Long payed180;
    /**
     * all 天有付款
     */
    private Long payedAll;
    /**
     * 90天有付款
     */
    private Long payed90;
    /**
     * 30天内发送过消息
     */
    private Long hasMessage30;
    /**
     * 28天未发送过消息或没有创建订单
     */
    private Long noMessage28;
    /**
     * 28天未创建订单
     */
    private Long noOrder28;

    /**
     * 标签级条件
     */
    private List<LabelRule> labelRules;

    @Data
    public static class LabelRule {

        /**
         * 关联条件code
         */
        private int conditionCode;

        /**
         * 关联类型 1000.全部商品 1100.指定商品 1200.指定类目
         */
        private Integer productAssociatedType;

        /**
         * 关联商品ids
         */
        private List<String> productIds;

        /**
         * 关联商品起始时间
         */
        private Date productAssociatedStartDate;

        /**
         * 行为类型 1 咨询商品 2 下单商品 3 付款商品
         */
        private int actionType;
    }


    @Data
    @ToString
    public static class OriginalCondition {

        /**
         * 最近付款时间-开始时间
         */
        private Date startPayedTime;

        /**
         * 最近付款时间-结束时间
         */
        private Date endPayedTime;

        /**
         * 最近咨询时间-开始时间
         */
        private Date startConsulTime;

        /**
         * 最近咨询时间-结束时间
         */
        private Date endConsulTime;

        /**
         * 付款金额-最小值
         */
        private Long minOrderPayed;

        /**
         * 付款金额-最大值
         */
        private Long maxOrderPayed;

        /**
         * 付款笔数-最小值
         */
        private Integer minOrderCount;

        /**
         * 付款笔数-最大值
         */
        private Integer maxOrderCount;

        /**
         * 客单价-最小值
         */
        private Long minAverageProduct;

        /**
         * 客单价-最大值
         */
        private Long maxAverageProduct;

        /**
         * 付款件数-最小值
         */
        private Integer minProductCount;

        /**
         * 付款件数-最大值
         */
        private Integer maxProductCount;
    }
}
