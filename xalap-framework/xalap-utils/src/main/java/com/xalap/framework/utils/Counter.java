/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.utils;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Счетчик
 *
 * @author Усов Андрей
 * @since 26.04.17
 */
public class Counter implements Comparable<Counter>{

    private final AtomicLong count = new AtomicLong();

    public Counter dec() {
        count.decrementAndGet();
        return this;
    }

    public Counter inc() {
        count.incrementAndGet();
        return this;
    }

    public Counter inc(long value) {
        count.addAndGet(value);
        return this;
    }

    public Counter inc(int value) {
        count.addAndGet(value);
        return this;
    }

    public Counter set(int value) {
        count.set(value);
        return this;
    }

    public Counter set(long value) {
        count.set(value);
        return this;
    }

    public long getCount() {
        return count.get();
    }

    @Override
    public int compareTo(Counter o) {
        return Long.compare(count.get(), o.getCount());
    }

    @Override
    public String toString() {
        return count.toString();
    }

    public static class CounterMap<K> {
        private final Map<K, Counter> map = new HashMap<>();

        public void inc(K key) {
            get(key).inc();
        }

        public void dec(K key) {
            get(key).dec();
        }

        private Counter get(K key) {
            Counter counter = map.get(key);
            if (counter == null) {
                counter = new Counter();
                map.put(key, counter);
            }
            return counter;
        }

        public boolean exist(K key) {
            return map.containsKey(key);
        }

        public void inc(Collection<K> keys) {
            keys.forEach(this::inc);
        }

        public void dec(Collection<K> keys) {
            keys.forEach(this::dec);
        }

        public void set(K key, int value) {
            get(key).set(value);
        }

        public void set(K key, long value) {
            get(key).set(value);
        }

        public Map<K, Counter> getMap() {
            return map;
        }

        public List<Map.Entry<K, Counter>> getSortedEntries() {
            List<Map.Entry<K, Counter>> result = new ArrayList<>();
            result.addAll(map.entrySet());
            Collections.sort(result, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
            return result;
        }

        public void clear(K key) {
            map.remove(key);
        }

        @Override
        public String toString() {
            return "CounterMap{" +
                    "map=" + map +
                    '}';
        }
    }
}
