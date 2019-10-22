package com.quyc.learn.javabasic.Algorithm.Collection;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by quyuanchao on 2019/3/7 0:45.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class LRUCache extends LinkedHashMap<String, Integer> {

    private static final int MAX = 3;

    public LRUCache() {
        super(MAX, 0.75f, true);
    }


    @Override
    protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
        return size() > 3;
    }
}
