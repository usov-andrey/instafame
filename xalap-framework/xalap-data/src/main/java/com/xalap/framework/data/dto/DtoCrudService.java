/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */
package com.xalap.framework.data.dto;

import com.xalap.framework.data.CrudService;
import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.framework.domain.page.PageableEntityService;
import com.xalap.framework.domain.page.request.PageRequest;
import com.xalap.framework.utils.ReflectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

/**
 * На входе сервис принимает Dto,
 * конвертирует его с помощью маппера
 * и делегирует вызов к другому сервису,
 * результат работы также конвертирует с помощью маппера
 * и отдает на выходе Dto.
 *
 * @author Usov Andrey
 * @since 14.05.2021
 */
public abstract class DtoCrudService<D extends IdHolder<I>, T extends IdHolder<I>, S extends CrudService<T, ?, I>, I>
        implements PageableEntityService<D, I> {

    @Autowired
    protected S service;
    private DtoMapper<D, T> mapper;

    protected DtoCrudService() {
        setMapper(new InterfaceDtoMapper<>());
    }

    public void setMapper(DtoMapper<D, T> mapper) {
        this.mapper = mapper;
    }

    protected D toDto(T object) {
        return mapper.toDto(object);
    }

    protected Optional<D> toDto(Optional<T> optionalObject) {
        return mapper.toDto(optionalObject);
    }


    protected T fromDto(D dto) {
        return mapper.fromDto(dto);
    }

    protected List<D> toDto(List<T> objects) {
        return mapper.toDto(objects);
    }

    @Override
    public D save(D bean) {
        return toDto(service.save(fromDto(bean)));
    }

    @Override
    public void delete(D bean) {
        service.delete(fromDto(bean));
    }

    @Override
    public void copy(D bean) {
        service.copy(fromDto(bean));
    }

    @Override
    public D get(I id) {
        return toDto(service.get(id));
    }

    @Override
    public boolean containsKey(D bean) {
        return service.containsKey(fromDto(bean));
    }

    @Override
    public List<D> getAll() {
        return toDto(service.getAll());
    }

    @Override
    @GetMapping("")
    public List<D> findPage(PageRequest pageable) {
        return toDto(service.findPage(pageable));
    }

    @Override
    @GetMapping("count")
    public long count() {
        return service.count();
    }

    @Override
    public D createNew() {
        return toDto(service.createNew());
    }

    @Override
    public Class<D> getBeanClass() {
        return ReflectHelper.getGenericParameterClass(getClass(), DtoCrudService.class, 0);
    }
}
