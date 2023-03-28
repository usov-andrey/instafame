/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.utils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Разные полезные функции с коллекциями
 */
public class CollectionHelper {

    private CollectionHelper() {
    }

    public static <K, V> List<V> getArrayListOrCreate(Map<K, List<V>> map, K key) {
        return map.computeIfAbsent(key, key1 -> new ArrayList<V>());
    }

    public static <K, V> List<V> getSafeArrayList(Map<K, List<V>> map, K key) {
        return map.getOrDefault(key, Collections.emptyList());
    }

    public static <K, V> Set<V> getHashSetOrCreate(Map<K, Set<V>> map, K key) {
        return map.computeIfAbsent(key, key1 -> new HashSet<>());
    }

    public static <K, V> Set<V> getSafeHashSet(Map<K, Set<V>> map, K key) {
        return map.getOrDefault(key, Collections.emptySet());
    }

    public static <K, MK, V> Map<MK, V> getHashMapOrCreate(Map<K, Map<MK, V>> map, K key) {
        return map.computeIfAbsent(key, key1 -> new HashMap<>());
    }

    public static <K, MK, V> Map<MK, V> getSafeHashMap(Map<K, Map<MK, V>> map, K key) {
        return map.getOrDefault(key, Collections.emptyMap());
    }

    @SafeVarargs
    public static <V> Set<V> newHashSet(V... values) {
        Set<V> set = new HashSet<>();
        Collections.addAll(set, values);
        return set;
	}

	public static <V> List<V> newArrayList(Collection<V> values) {
		List<V> list = new ArrayList<>();
		list.addAll(values);
		return list;
	}

    public static <V, T> Set<V> newHashSet(Collection<T> values, Function<T, V> function) {
        return values.stream().map(function).collect(Collectors.toSet());
    }

    public static <V, T> List<V> newArrayList(Collection<T> values, Function<T, V> function) {
        return newCollection(values, function, new ArrayList<>());
    }

    public static <V, T> Queue<V> newQueue(Collection<T> values, Function<T, V> function) {
        return newCollection(values, function, newConcurrentQueue());
    }

    public static <V, T, C extends Collection<V>> C newCollection(Collection<T> values, Function<T, V> function, C resultCollection) {
        for (T value : values) {
            V result = function.apply(value);
            if (result != null) {
                resultCollection.add(result);
            }
        }
        return resultCollection;
    }



    public static <V, T> List<V> newArrayList(T[] values, Function<T, V> function) {
        return newArrayList(Arrays.asList(values), function);
    }

	public static <T> String toString(Collection<T> objects, Function<T, String> toStringFunction) {
        return StringHelper.join(objects, toStringFunction);
    }

    public static <V> Set<V> newConcurrentSet() {
        return Collections.newSetFromMap(new ConcurrentHashMap<V, Boolean>());
    }

    public static <T> Queue<T> newConcurrentQueue() {
        return new ConcurrentLinkedQueue<>();
    }

    public static <T> Iterator<T> repeatIterator(Collection<T> collection) {
        return new Iterator<T>() {

            private Iterator<T> iterator;
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                return doNext();
            }

            private synchronized T doNext() {
                if (iterator == null || !iterator.hasNext()) {
                    iterator = collection.iterator();
                }
                return iterator.next();
            }
        };
    }

    public static <T> boolean intersect(Collection<T> collection, Collection<T> collection2) {
        for (T object1 : collection) {
            for (T object2 : collection2) {
                if (object1.equals(object2)) {
                    return true;
                }
             }
        }
        return false;
    }

    /**
     * Возвращаем список ключей K отсортированный с помощью значений V
     */
    public static <K,V> List<K> keysSortedByValues(Map<K, V> map, Comparator<V> comparator) {
        List<Map.Entry<K,V>> entries = new ArrayList<>(map.entrySet());
        entries.sort((o1, o2) -> comparator.compare(o1.getValue(), o2.getValue()));
        return newArrayList(entries, Map.Entry::getKey);
    }

    public static <K,V,S> Map<K, List<V>> createMultiMap(Collection<S> sourceCollection, Function<S, K> keyFunction, Function<S, V> valueFunction) {
        Map<K, List<V>> map = new HashMap<>();
        for (S source : sourceCollection) {
            getArrayListOrCreate(map, keyFunction.apply(source)).add(valueFunction.apply(source));
        }
        return map;
    }

    public static <K,V> Map<K, List<V>> createMultiMap(Collection<V> sourceCollection, Function<V, K> keyFunction) {
        return createMultiMap(sourceCollection, keyFunction, v -> v);
    }

    public static <K, V> Map<K, V> createIdMap(Collection<V> sourceCollection, Function<V, K> keyFunction) {
        return sourceCollection.stream().collect(Collectors.toMap(keyFunction, Function.identity()));
    }

    public static <T> int count(List<T> objects, Function<T, Integer> countFunction) {
        int result = 0;
        for (T object : objects) {
            result += countFunction.apply(object);
        }
        return result;
    }

    public static <T> double countDouble(List<T> objects, Function<T, Double> countFunction) {
        int result = 0;
        for (T object : objects) {
            result += countFunction.apply(object);
        }
        return result;
    }

    public static <T extends Comparable<? super T>> List<T> sorted(Collection<T> objects) {
        List<T> result = new ArrayList<>(objects);
        Collections.sort(result);
        return result;
    }

    public static <T> Double sumDouble(Collection<T> objects, Function<T, Double> valueFunction) {
        return objects.stream().filter(orderBean -> valueFunction.apply(orderBean) != null)
                .mapToDouble(valueFunction::apply).sum();
    }

    public static <T> OptionalDouble avgDouble(Collection<T> objects, Function<T, Double> valueFunction) {
        return objects.stream().filter(orderBean -> valueFunction.apply(orderBean) != null)
                .mapToDouble(valueFunction::apply).average();
    }

    public static <T> Long sumLong(Collection<T> objects, Function<T, Long> valueFunction) {
        return objects.stream().filter(orderBean -> valueFunction.apply(orderBean) != null)
                .mapToLong(valueFunction::apply).sum();
    }

    public static <T> Integer sumInt(Collection<T> objects, Function<T, Integer> valueFunction) {
        return objects.stream().filter(orderBean -> valueFunction.apply(orderBean) != null)
                .mapToInt(valueFunction::apply).sum();
    }

    public static <T> long count(Collection<T> objects, Predicate<T> predicate) {
        return objects.stream().filter(predicate).count();
    }

    /**
     * Есть Collection<Source> и в Source есть поле, которое нужно заполнить
     * Но заполнять нужно одним запросом для всей коллекции
     */
    public static <Source, Key, Value> void fillFK(Collection<Source> source, Function<Source, Key> keyFunction,
                                                   Function<Collection<Key>, Collection<Value>> valuesFunction,
                                                   Function<Value, Key> valueKeyFunction,
                                                   BiConsumer<Source, Value> sourceValueBiConsumer
    ) {
        Set<Key> keys = source.stream().map(keyFunction).collect(Collectors.toSet());
        Collection<Value> values = keys.isEmpty() ? new ArrayList<Value>() : valuesFunction.apply(keys);
        Map<Key, Value> valueMap = new HashMap<Key, Value>();
        for (Value value : values) {
            valueMap.put(valueKeyFunction.apply(value), value);
        }
        for (Source sourceValue : source) {
            Key key = keyFunction.apply(sourceValue);
            Value value = valueMap.get(key);
            if (value != null) {
                sourceValueBiConsumer.accept(sourceValue, value);
            }
        }
    }
}
