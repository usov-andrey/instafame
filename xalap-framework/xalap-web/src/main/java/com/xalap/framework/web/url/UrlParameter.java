/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.web.url;

/**
 *
 * @author Usov Andrey
 * @since 29.12.2020
 */
public interface UrlParameter<T> {

    T fromUrl(RelativeUrl url);

}
