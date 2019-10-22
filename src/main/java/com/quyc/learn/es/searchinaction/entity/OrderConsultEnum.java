package com.quyc.learn.es.searchinaction.entity;

/**
 * 描述
 * 咨询 下单 付款tag
 * @author Created by mayu
 * @create 2019-01-2019/1/16
 */
public enum OrderConsultEnum {
    // 咨询 下单 付款tag
    CONSULT_7(1L, "7天有咨询", new Invoke<BuyerPortraitQuery>(){
        @Override
        public void buildQuery(BuyerPortraitQuery buyerPortraitQuery){
            if (null != buyerPortraitQuery && null != buyerPortraitQuery.getOrderConsultTag()){
                buyerPortraitQuery.setConsult7(CONSULT_7.getCode().equals(buyerPortraitQuery.getOrderConsultTag() & CONSULT_7.getCode()) ? 1L: 0L);
            }
        }
    }),
    CONSULT_15(2L, "15天有咨询", new Invoke<BuyerPortraitQuery>(){
        @Override
        public void buildQuery(BuyerPortraitQuery buyerPortraitQuery){
            if (null != buyerPortraitQuery && null != buyerPortraitQuery.getOrderConsultTag()){
                buyerPortraitQuery.setConsult15(CONSULT_15.getCode().equals(buyerPortraitQuery.getOrderConsultTag() & CONSULT_15.getCode()) ? 1L: 0L);
            }
        }
    }),
    CONSULT_30(4L, "30天有咨询", new Invoke<BuyerPortraitQuery>(){
        @Override
        public void buildQuery(BuyerPortraitQuery buyerPortraitQuery){
            if (null != buyerPortraitQuery && null != buyerPortraitQuery.getOrderConsultTag()){
                buyerPortraitQuery.setConsult30(CONSULT_30.getCode().equals(buyerPortraitQuery.getOrderConsultTag() & CONSULT_30.getCode())? 1L: 0L);
            }
        }
    }),
    CONSULT_ALL(8L, "有咨询", new Invoke<BuyerPortraitQuery>(){
        @Override
        public void buildQuery(BuyerPortraitQuery buyerPortraitQuery){
            if (null != buyerPortraitQuery && null != buyerPortraitQuery.getOrderConsultTag()){
                buyerPortraitQuery.setConsultAll(CONSULT_ALL.getCode().equals(buyerPortraitQuery.getOrderConsultTag() & CONSULT_ALL.getCode()) ? 1L: 0L);
            }
        }
    }),
    UN_ORDER_7(16L, "7天未下单", new Invoke<BuyerPortraitQuery>(){
        @Override
        public void buildQuery(BuyerPortraitQuery buyerPortraitQuery){
            if (null != buyerPortraitQuery && null != buyerPortraitQuery.getOrderConsultTag()){
                buyerPortraitQuery.setUnOrder7(UN_ORDER_7.getCode().equals(buyerPortraitQuery.getOrderConsultTag() & UN_ORDER_7.getCode()) ?1L:0L);
            }
        }
    }),
    UN_ORDER_15(32L, "15天未下单", new Invoke<BuyerPortraitQuery>(){
        @Override
        public void buildQuery(BuyerPortraitQuery buyerPortraitQuery){
            if (null != buyerPortraitQuery && null != buyerPortraitQuery.getOrderConsultTag()){
                buyerPortraitQuery.setUnOrder15(UN_ORDER_15.getCode().equals(buyerPortraitQuery.getOrderConsultTag() & UN_ORDER_15.getCode()) ?1L:0L);
            }
        }
    }),
    UN_ORDER_30(64L, "30天未下单", new Invoke<BuyerPortraitQuery>(){
        @Override
        public void buildQuery(BuyerPortraitQuery buyerPortraitQuery){
            if (null != buyerPortraitQuery && null != buyerPortraitQuery.getOrderConsultTag()){
                buyerPortraitQuery.setUnOrder30(UN_ORDER_30.getCode().equals(buyerPortraitQuery.getOrderConsultTag() & UN_ORDER_30.getCode()) ?1L:0L);
            }
        }
    }),
    UN_ORDER_ALL(128L, "未下单", new Invoke<BuyerPortraitQuery>(){
        @Override
        public void buildQuery(BuyerPortraitQuery buyerPortraitQuery){
            if (null != buyerPortraitQuery && null != buyerPortraitQuery.getOrderConsultTag()){
                buyerPortraitQuery.setUnOrderAll(UN_ORDER_ALL.getCode().equals(buyerPortraitQuery.getOrderConsultTag() & UN_ORDER_ALL.getCode()) ?1L:0L);
            }
        }
    }),
    PAYED_30(256L, "30天有付款", new Invoke<BuyerPortraitQuery>(){
        @Override
        public void buildQuery(BuyerPortraitQuery buyerPortraitQuery){
            if (null != buyerPortraitQuery && null != buyerPortraitQuery.getOrderConsultTag()){
                buyerPortraitQuery.setPayed30(PAYED_30.getCode().equals(buyerPortraitQuery.getOrderConsultTag() & PAYED_30.getCode()) ?1L:0L);
            }
        }
    }),
    PAYED_60(512L, "60天有付款", new Invoke<BuyerPortraitQuery>(){
        @Override
        public void buildQuery(BuyerPortraitQuery buyerPortraitQuery){
            if (null != buyerPortraitQuery && null != buyerPortraitQuery.getOrderConsultTag()){
                buyerPortraitQuery.setPayed60(PAYED_60.getCode().equals(buyerPortraitQuery.getOrderConsultTag() & PAYED_60.getCode()) ?1L:0L);
            }
        }
    }),
    PAYED_90(1024L, "90天有付款", new Invoke<BuyerPortraitQuery>(){
        @Override
        public void buildQuery(BuyerPortraitQuery buyerPortraitQuery){
            if (null != buyerPortraitQuery && null != buyerPortraitQuery.getOrderConsultTag()){
                buyerPortraitQuery.setPayed90(PAYED_90.getCode().equals(buyerPortraitQuery.getOrderConsultTag() & PAYED_90.getCode()) ?1L:0L);
            }
        }
    }),
    PAYED_180(2048L, "180天有付款", new Invoke<BuyerPortraitQuery>(){
        @Override
        public void buildQuery(BuyerPortraitQuery buyerPortraitQuery){
            if (null != buyerPortraitQuery && null != buyerPortraitQuery.getOrderConsultTag()){
                buyerPortraitQuery.setPayed180(PAYED_180.getCode().equals(buyerPortraitQuery.getOrderConsultTag() & PAYED_180.getCode()) ?1L:0L);
            }
        }
    }),
    PAYED_ALL(4096L, "有付款", new Invoke<BuyerPortraitQuery>(){
        @Override
        public void buildQuery(BuyerPortraitQuery buyerPortraitQuery){
            if (null != buyerPortraitQuery && null != buyerPortraitQuery.getOrderConsultTag()){
                buyerPortraitQuery.setPayedAll(PAYED_ALL.getCode().equals(buyerPortraitQuery.getOrderConsultTag() & PAYED_ALL.getCode()) ?1L:0L);
            }
        }
    }),
    HAS_MESSAGE_30(8192L, "30天内发送过消息", new Invoke<BuyerPortraitQuery>(){
        @Override
        public void buildQuery(BuyerPortraitQuery buyerPortraitQuery){
            if (null != buyerPortraitQuery && null != buyerPortraitQuery.getOrderConsultTag()){
                buyerPortraitQuery.setHasMessage30(HAS_MESSAGE_30.getCode().equals(buyerPortraitQuery.getOrderConsultTag() & HAS_MESSAGE_30.getCode()) ?1L:0L);
            }
        }
    }),
    NO_MESSAGE_28(16384L, "28天未发送过消息或没有创建订单", new Invoke<BuyerPortraitQuery>(){
        @Override
        public void buildQuery(BuyerPortraitQuery buyerPortraitQuery){
            if (null != buyerPortraitQuery && null != buyerPortraitQuery.getOrderConsultTag()){
                buyerPortraitQuery.setNoMessage28(NO_MESSAGE_28.getCode().equals(buyerPortraitQuery.getOrderConsultTag() & NO_MESSAGE_28.getCode())?1L:0L);
            }
        }
    }),
    NO_ORDER_28(32768L, "28天未创建订单", new Invoke<BuyerPortraitQuery>(){
        @Override
        public void buildQuery(BuyerPortraitQuery buyerPortraitQuery){
            if (null != buyerPortraitQuery && null != buyerPortraitQuery.getOrderConsultTag()){
                buyerPortraitQuery.setNoOrder28(NO_ORDER_28.getCode().equals(buyerPortraitQuery.getOrderConsultTag() & NO_ORDER_28.getCode())?1L:0L);
            }
        }
    });


    OrderConsultEnum(Long code, String desc, Invoke invoke){
        this.code = code;
        this.desc = desc;
        this.invoke = invoke;
    }

    private Long code;
    private String desc;
    private Invoke invoke;

    public static OrderConsultEnum getByCode(Long code){
        if (null == code){
            return null;
        }
        for (OrderConsultEnum orderConsultEnum : OrderConsultEnum.values()){
            if (orderConsultEnum.getCode().equals(code)){
                return orderConsultEnum;
            }
        }
        return null;
    }

    public Long getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public interface Invoke<QUERY> {
        void buildQuery(QUERY objectQUERY);
    }


    /**
     * 对外的buildquery方法
     * @param buyerPortraitQuery
     * @return
     */
    public static BuyerPortraitQuery buildOrderConsultQuery(BuyerPortraitQuery buyerPortraitQuery){
        if (null == buyerPortraitQuery || null == buyerPortraitQuery.getOrderConsultTag()){
            return buyerPortraitQuery;
        }

        for (OrderConsultEnum orderConsultEnum: values()){
            orderConsultEnum.invoke.buildQuery(buyerPortraitQuery);
        }

        return buyerPortraitQuery;
    }





}
