package org.fzsoft.shoppingmall.utils.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by yhj on 17/4/18.
 */
public class MutliTreeMap<K, V> {


    private ConcurrentSkipListMap<K, Entry> skipListMap = new ConcurrentSkipListMap<K, Entry>();


    public void put(K key, V value) {
        Entry entry = getValueCollection(key);

        entry.add(value);
    }

    public boolean remove(K key, V value) {

        Entry entry = getValueCollection(key);

        return entry.remove(value);
    }

    public synchronized List<V> subKey(K fromKey, K toKey, boolean fromInclude, boolean toInclude) {
        ConcurrentNavigableMap<K, Entry> map = skipListMap.subMap(fromKey, fromInclude, toKey, toInclude);


        List<V> list = new ArrayList<>();

        for (Entry entry : map.values()) {
            list.addAll(entry.entries);
        }

        return Collections.unmodifiableList(list);
    }


    private synchronized Entry getValueCollection(K key) {
        Entry entry = skipListMap.get(key);

        if (entry == null) {
            skipListMap.put(key, (entry = new Entry()));
        }
        return entry;
    }


    class Entry {
        HashSet<V> entries = new HashSet<V>();


        public void add(V v) {

            entries.add(v);
        }

        public boolean remove(V v) {
            return entries.remove(v);
        }

        public boolean isEmpty() {
            return entries.isEmpty();
        }

    }

}
