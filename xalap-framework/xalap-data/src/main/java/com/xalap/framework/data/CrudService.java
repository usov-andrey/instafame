/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.data;

import com.xalap.framework.data.listener.CrudOperation;
import com.xalap.framework.data.page.RepositoryTypeConverter;
import com.xalap.framework.data.page.repository.PageableRepository;
import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.framework.domain.page.PageableEntityService;
import com.xalap.framework.domain.page.request.PageRequest;
import com.xalap.framework.utils.ReflectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Слой бизнес логики для работы с какой-то сущностью
 *
 * @author Усов Андрей
 * @since 24.06.2018
 */
public abstract class CrudService<T extends IdHolder<ID>, R extends PageableRepository<T, ID>, ID>
        implements PageableEntityService<T, ID>, RepositoryTypeConverter {

    @Autowired(required = false)
    CacheManager cacheManager;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private R repository;
    @Autowired
    private CrudListenersService crudListenersService;

    @EventListener({ContextRefreshedEvent.class})
    public void internalInit() {
        crudListenersService.addCrudService(getBeanClass(), this);
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public boolean contains(ID key) {
        return repository.findById(key).isPresent();
    }

    /**
     * Данный метод может вернуть значение из кэша
     */
    @Override
    public T get(ID key) throws NotFoundException {
        return repository.findById(key)
                .orElseThrow(() ->
                        new NotFoundException("In " + repository() + " not found by Id:" + key));
    }

    public Map<ID, T> get(Set<ID> keys) {
        Map<ID, T> result = new HashMap<>();
        repository.findAllById(keys).forEach(t -> result.put(t.getId(), t));
        return result;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public T getFromDB(ID key) {
        return get(key);
    }

    @Override
    public void delete(T bean) {
        ID key = bean.getId();
        crudListenersService.before(CrudOperation.delete, bean);
        repository.deleteById(key);
        onChange(bean);
        crudListenersService.after(CrudOperation.delete, bean);
    }

    @Transactional
    @Override
    public T save(T bean) {
        try {
            crudListenersService.before(CrudOperation.save, bean);
            T newBean = repository.save(bean);
            onChange(newBean);
            crudListenersService.after(CrudOperation.save, bean);
            return newBean;
        } catch (Exception e) {
            throw new IllegalStateException("Error on saveBean:" + bean, e);
        }
    }

    protected R getRepository() {
        return repository;
    }

    @Deprecated
    /*
     * Нельзя использовать этот метод, так как дальше сильно затруднится разбиение на микросервисы
     * @deprecated нужно обращаться к методам сервиса
     */
    public R repository() {
        return repository;
    }

    @Override
    public List<T> findPage(PageRequest pageRequest) {
        return repository.find(pageable(pageRequest));
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void copy(T bean) {
        throw new UnsupportedOperationException();
    }

    protected void onChange(T bean) {

    }

    private Cache getCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            throw new IllegalStateException("Not found cache by name:" + cacheName);
        }
        return cache;
    }

    public void evict(String cacheName, String cacheKey) {
        getCache(cacheName).evict(cacheKey);
    }

    public void evictAll(String cacheName) {
        getCache(cacheName).clear();
    }

    /**
     * Дозагружает list данными
     */
    public <V> void load(List<V> list, Function<V, ID> idMapper, BiConsumer<V, T> setter) {
        Set<ID> idSet = list.stream().map(idMapper).collect(Collectors.toSet());
        Map<ID, T> beans = get(idSet);
        list.forEach(sourceBean -> setter.accept(sourceBean, beans.get(idMapper.apply(sourceBean))));
    }

    @Override
    public Class<T> getBeanClass() {
        return ReflectHelper.getGenericParameterClass(getClass(), CrudService.class, 0);
    }

    @Override
    public T createNew() {
        return ReflectHelper.newInstance(getBeanClass());
    }
}
