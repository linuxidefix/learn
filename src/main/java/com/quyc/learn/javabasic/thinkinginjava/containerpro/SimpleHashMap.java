package com.quyc.learn.javabasic.thinkinginjava.containerpro;//: containers/SimpleHashMap.java
// A demonstration hashed Map.


import com.quyc.learn.javabasic.thinkinginjava.net.mindview.util.Countries;

import java.util.*;

public class SimpleHashMap<K, V> extends AbstractMap<K, V> {
    // Choose a prime number for the hash table size, to achieve a uniform distribution:
    static final int SIZE = 997;
    // You can't have a physical array of generics, but you can upcast to one:
    @SuppressWarnings("unchecked")
    LinkedList<MapEntry<K, V>>[] buckets =
            new LinkedList[SIZE];

    @Override
    public V put(K key, V value) {
        V oldValue = null;
        // 通过对key进行散列计算这个key应该落入的bucket
        int index = Math.abs(key.hashCode()) % SIZE;
        // 若这个位置没有，则新建一个LinkedList用于保存对应的键值对
        if (buckets[index] == null) {
            buckets[index] = new LinkedList<MapEntry<K, V>>();
        }
        LinkedList<MapEntry<K, V>> bucket = buckets[index];
        MapEntry<K, V> pair = new MapEntry<K, V>(key, value);
        // 用于判断keySet中是否存在这个key
        boolean found = false;
        ListIterator<MapEntry<K, V>> it = bucket.listIterator();
        // 遍历判断这个key是否已经存在
        while (it.hasNext()) {
            MapEntry<K, V> iPair = it.next();
            if (iPair.getKey().equals(key)) {
                // 若已经存在，则取到旧值
                oldValue = iPair.getValue();
                // 放入新值
                it.set(pair); // Replace old with new
                // 更新查找标识为true
                found = true;
                break;
            }
        }
        // 若未找到，则插入新值
        if (!found) {
            buckets[index].add(pair);
        }
        return oldValue;
    }

    @Override
    public V get(Object key) {
        // 适用于插入是相同的散列计算方法计算bucket位置
        int index = Math.abs(key.hashCode()) % SIZE;
        // 若位置为空，则直接返回null
        if (buckets[index] == null) {
            return null;
        }
        // 若存在bucket，则遍历寻找对应的value
        for (MapEntry<K, V> iPair : buckets[index]) {
            if (iPair.getKey().equals(key)) {
                return iPair.getValue();
            }
        }
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<Entry<K, V>>();
        for (LinkedList<MapEntry<K, V>> bucket : buckets) {
            if (bucket == null) {
                continue;
            }
            for (MapEntry<K, V> mpair : bucket) {
                set.add(mpair);
            }
        }
        return set;
    }

    public static void main(String[] args) {
        SimpleHashMap<String, String> m =
                new SimpleHashMap<String, String>();
        m.putAll(Countries.capitals(25));
        System.out.println(m);
        System.out.println(m.get("ERITREA"));
        System.out.println(m.entrySet());
    }
} /* Output:
{CAMEROON=Yaounde, CONGO=Brazzaville, CHAD=N'djamena, COTE D'IVOIR (IVORY COAST)=Yamoussoukro, CENTRAL AFRICAN REPUBLIC=Bangui, GUINEA=Conakry, BOTSWANA=Gaberone, BISSAU=Bissau, EGYPT=Cairo, ANGOLA=Luanda, BURKINA FASO=Ouagadougou, ERITREA=Asmara, THE GAMBIA=Banjul, KENYA=Nairobi, GABON=Libreville, CAPE VERDE=Praia, ALGERIA=Algiers, COMOROS=Moroni, EQUATORIAL GUINEA=Malabo, BURUNDI=Bujumbura, BENIN=Porto-Novo, BULGARIA=Sofia, GHANA=Accra, DJIBOUTI=Dijibouti, ETHIOPIA=Addis Ababa}
Asmara
[CAMEROON=Yaounde, CONGO=Brazzaville, CHAD=N'djamena, COTE D'IVOIR (IVORY COAST)=Yamoussoukro, CENTRAL AFRICAN REPUBLIC=Bangui, GUINEA=Conakry, BOTSWANA=Gaberone, BISSAU=Bissau, EGYPT=Cairo, ANGOLA=Luanda, BURKINA FASO=Ouagadougou, ERITREA=Asmara, THE GAMBIA=Banjul, KENYA=Nairobi, GABON=Libreville, CAPE VERDE=Praia, ALGERIA=Algiers, COMOROS=Moroni, EQUATORIAL GUINEA=Malabo, BURUNDI=Bujumbura, BENIN=Porto-Novo, BULGARIA=Sofia, GHANA=Accra, DJIBOUTI=Dijibouti, ETHIOPIA=Addis Ababa]
*///:~
