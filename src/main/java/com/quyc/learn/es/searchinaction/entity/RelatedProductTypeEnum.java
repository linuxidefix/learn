package com.quyc.learn.es.searchinaction.entity;

import lombok.Getter;

/**
 * 关联商品类型枚举
 *
 * @author Created by hawei
 * @create 2019/4/22 3:35 PM
 */
@Getter
public enum RelatedProductTypeEnum {
    /**
     * 全部商品
     */
    ALL(1000, "全部商品"),
    PRODUCT(1100, "指定商品"),
    CATEGORY(1200, "指定分类");

    private Integer value;

    private String label;

    RelatedProductTypeEnum(Integer value, String label) {
        this.value = value;

        this.label = label;
    }

}

