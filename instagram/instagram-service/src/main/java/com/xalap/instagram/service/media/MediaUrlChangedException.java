/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.media;

/**
 * Со временем ссылки на превью устаревают(видать там зависит от кэша)
 * поэтому нужно в такой ситуации перечитывать информацию о media
 *
 * @author Усов Андрей
 * @since 01.08.2018
 */
public class MediaUrlChangedException extends RuntimeException {

}
