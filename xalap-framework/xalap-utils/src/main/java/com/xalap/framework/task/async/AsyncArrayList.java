/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.task.async;

import com.xalap.framework.spring.BeanFactory;
import com.xalap.framework.task.TaskCallable;
import com.xalap.framework.task.TaskRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Список, в который можно добавлять значения, которые будут выполнены на этапе обращения к этому списку,
 * а не на этапе добавления
 *
 * @author Андрей
 * @since 27.09.15
 */
public class AsyncArrayList<T> extends ArrayList<T> {

    private Collection<TaskCallable<Collection<T>>> callableList;

    public void addAllAsync(TaskCallable<Collection<T>> callable) {
        if (callableList == null) {
            callableList = new ArrayList<>();
        }
        callableList.add(callable);
    }

    protected void checkInit() {
        if (callableList != null) {
            Collection<Collection<T>> callResult = getTaskRunner().call(callableList);
            callableList = null;
            callResult.forEach(super::addAll);
        }
    }

    protected TaskRunner getTaskRunner() {
        return BeanFactory.get(TaskRunner.class);
    }

    @Override
    public int size() {
        this.checkInit();
        return super.size();
    }

    @Override
    public boolean isEmpty() {
        this.checkInit();
        return super.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        this.checkInit();
        return super.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        this.checkInit();
        return super.iterator();
    }

    @Override
    public Object[] toArray() {
        this.checkInit();
        return super.toArray();
    }

    @Override
    public <V> V[] toArray(V[] a) {
        this.checkInit();
        return super.toArray(a);
    }

    @Override
    public boolean add(T o) {
        this.checkInit();
        return super.add(o);
    }

    @Override
    public boolean remove(Object o) {
        this.checkInit();
        return super.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        this.checkInit();
        return super.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        this.checkInit();
        return super.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        this.checkInit();
        return super.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        this.checkInit();
        return super.retainAll(c);
    }

    @Override
    public void clear() {
        this.checkInit();
        super.clear();
    }

    @Override
    public String toString() {
        this.checkInit();
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        this.checkInit();
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        this.checkInit();
        return super.hashCode();
    }

}
