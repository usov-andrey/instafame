/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.data.logging;

import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.framework.domain.holder.IdHolderWithName;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Усов Андрей
 * @since 27/06/2019
 */
@Aspect
@Component
public class RepositoryLoggingAspect {

    private final Logger log = LoggerFactory.getLogger("repository");


    @Pointcut("this(org.springframework.data.repository.PagingAndSortingRepository)")
    public void repositoryPointcut() {

    }

    @Around("repositoryPointcut()")
    public Object logRepository(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        boolean isTrace = log.isTraceEnabled();
        if (methodName.contains("toString")) {
            isTrace = false;
        }
        if (isTrace) {
            String className = joinPoint.getSignature().getDeclaringTypeName();
            log.trace("Enter: {}.{}() with argument[s] = {}", className,
                    methodName, toString(joinPoint.getArgs()));
            //log.trace(ReflectHelper.getCurrentStackTrace());
        }
        try {
            Object result = joinPoint.proceed();
            if (isTrace) {
                if (result instanceof Collection) {
                    log.trace("Exit: result.size=" + ((Collection) result).size());
                } else {
                    log.trace("Exit: result={}", toString(result));
                }
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), methodName);
            throw e;
        }
    }

    private String toString(Object object) {
        if (object == null) {
            return "null";
        }
        if (object instanceof Optional) {
            Optional optional = (Optional) object;
            return optional.isPresent()
                    ? String.format("Optional[%s]", toString(optional.get()))
                    : "Optional.empty";
        }
        if (object instanceof IdHolderWithName) {
            return toString((IdHolderWithName) object);
        }
        if (object instanceof IdHolder) {
            return toString((IdHolder) object);
        }
        return String.valueOf(object);
    }

    private String toString(IdHolderWithName idHolderWithName) {
        return idHolderWithName.getClass().getSimpleName() + "{id=" + idHolderWithName.idToString() + ", name=" +
                idHolderWithName.getName() +
                "}";
    }

    private String toString(IdHolder idHolder) {
        return idHolder.getClass().getSimpleName() + "{id=" + idHolder.idToString() + "}";
    }

    private String toString(Object[] a) {
        if (a == null)
            return "null";

        int iMax = a.length - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            if (a[i] instanceof Collection) {
                Collection collection = (Collection) a[i];
                b.append(a[i].getClass().getSimpleName());
                if (collection.size() > 0) {
                    b.append("<").append(collection.iterator().next().getClass().getSimpleName()).append(">");
                }
                b.append(":").append(collection.size());
            } else {
                b.append(toString(a[i]));
            }
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }
}

