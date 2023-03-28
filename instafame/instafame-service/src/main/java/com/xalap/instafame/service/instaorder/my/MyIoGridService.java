/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.my;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.framework.data.page.RepositoryTypeConverter;
import com.xalap.framework.domain.page.dataprovider.PageableDataProviderResolver;
import com.xalap.framework.domain.page.dataprovider.filter.FilterDataProviderResolver;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.IORepository;
import com.xalap.instafame.service.instaorder.IOStats;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.provider.MemoryIOTaskProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.xalap.framework.domain.page.dataprovider.PageableDataProvider.page;

/**
 * @author Usov Andrey
 * @since 2020-05-14
 */
@Component
public class MyIoGridService implements RepositoryTypeConverter {

    private final IORepository ioRepository;
    private final IOTaskService ioTaskService;

    public MyIoGridService(IORepository ioRepository, IOTaskService ioTaskService) {
        this.ioRepository = ioRepository;
        this.ioTaskService = ioTaskService;
    }

    public PageableDataProviderResolver<MyIoGridBean> noFilter(Supplier<ContactBean> contactBeanSupplier) {
        return () -> page(
                pageable -> {

                    List<IOBean> ioBeans = ioRepository.findByContact(pageable(pageable), contactBeanSupplier.get(), MyIoStatus.all.statuses(),
                            MyIoType.all.isEmpty(), MyIoType.all.getType());
                    return convert(ioBeans);
                },
                () -> ioRepository.countByContact(contactBeanSupplier.get(), MyIoStatus.all.statuses(),
                        MyIoType.all.isEmpty(), MyIoType.all.getType())
        );
    }

    public FilterDataProviderResolver<MyIoGridBean, MyIoFilter> byFilter(Supplier<ContactBean> contactBeanSupplier) {
        return filter -> page(
                pageable -> {
                    List<IOBean> ioBeans = ioRepository.findByContact(pageable(pageable), contactBeanSupplier.get(),
                            filter.getStatus().statuses(),
                            filter.getTaskType().isEmpty(), filter.getTaskType().getType());
                    return convert(ioBeans);
                },
                () -> ioRepository.countByContact(contactBeanSupplier.get(), filter.getStatus().statuses(),
                        filter.getTaskType().isEmpty(), filter.getTaskType().getType()));
    }

    private List<MyIoGridBean> convert(List<IOBean> ioBeans) {
        List<MyIoGridBean> gridBeans = new ArrayList<>();
        MemoryIOTaskProvider taskProvider = ioTaskService.memoryTaskProvider(ioBeans);

        for (IOBean ioBean : ioBeans) {
            MyIoGridBean gridBean = new MyIoGridBean(ioBean);
            gridBean.setIoStats(new IOStats(ioBean, taskProvider.getTasks(ioBean)));
            gridBeans.add(gridBean);
        }
        return gridBeans;
    }

}
