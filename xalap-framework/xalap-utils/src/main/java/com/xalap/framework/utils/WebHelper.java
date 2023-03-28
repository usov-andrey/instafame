/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Разные полезные функции по работе с web
 */
public class WebHelper {

    public static final String ENCODING = "UTF-8";
    public static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;
    public static final String URL_SEPARATOR = "/";
    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";
    private static final String WWW = "www.";
    private static final String URL_PARAMS_SEPARATOR = "&";

    private WebHelper() {
    }

    public static URL url(String url) {
        try {
            return new URL(url);
        } catch (Exception e) {
            throw new IllegalStateException("Error on create URL:" + url, e);
        }
    }

    /**
	 * @return адрес не заканчивающийся / и не начинающийся с /
	 */
	public static String getURLWithoutHttp(String... values) {
		StringBuilder sb = new StringBuilder();
		String lastValue = null;
		for (String value : values) {
			if (value != null) {
				if (sb.length() > 0 && (lastValue == null || !lastValue.endsWith(URL_SEPARATOR))) {
					sb.append(URL_SEPARATOR);
				}
                if (lastValue != null && value.startsWith(URL_SEPARATOR)) {
                    value = value.substring(1);
                }
				sb.append(value);
				lastValue = value;
			}
		}
		return sb.toString();
	}

	/**
	 * @return адрес не заканчивающийся / и не начинающийся с /
	 */
	public static String getURL(String... values) {
		return getURLWithoutHttp(values);
	}

	public static String getHttpURL(String domain) {
		return HTTP_PREFIX + domain;
	}

	public static String cutZone(String url) {
		int index = url.lastIndexOf(".");
		if (index > -1) {
			return url.substring(0, index);
		} else {
			return "";
		}
	}

	public static String getZone(String url) {
		String zone = getDomainLastPart(url);
		if (zone.contains("/")) {
			zone = StringHelper.getStringBefore(zone, "/");
		}
		return zone;
	}

	public static String getDomain(String url) {
		String domain = getURLWithoutProtocol(url);
		if (domain.contains("/")) {
			domain = StringHelper.getStringBefore(domain, "/");
		}
		//Вырезаем www.
		if (domain.startsWith(WWW)) {
			domain = StringHelper.getStringAfter(domain, WWW);
		}
		return domain;
	}

	public static boolean isURLEndsWith(String url, String value) {
		return url.endsWith(value) || url.endsWith(value + "/");
	}

	public static String getURLWithoutProtocol(String url) {
		if (url.startsWith("//")) {
			return url.substring(2);
		}
		String domain = StringHelper.getStringAfter(url, HTTP_PREFIX);
		if (StringHelper.isEmpty(domain)) {
			domain = StringHelper.getStringAfter(url, HTTPS_PREFIX);
			if (StringHelper.isEmpty(domain)) {
				domain = url;
			}
		}
		return domain;
	}

	/**
	 * Получаем двух уровневое доменное имя, например для plus.google.com
	 * возвращаем google.com, а для plus.google.co.th возвращаем google.co.th, для my.plus.google.com возвращаем google.com
	 */
	public static String get2LevelDomain(String domain) {
		String[] values = domain.split("\\.");
		StringBuilder sb = new StringBuilder();
		int index = values.length - 1;
		int secondPartSize = 0;
		for (int i = index; i >= 0; i--) {
			String value = values[i];
			if (sb.length() == 0) {
				sb.append(value);
			} else {
				//внутри уже есть com или th
				sb.insert(0, value + ".");
				if (secondPartSize == 0) {
					secondPartSize = value.length();
				}
				//Внутри уже или google.com или co.th и secondPart или google.length или co.length
				if (secondPartSize > 3) {
					break;
				} else {
					secondPartSize = 10;//в следующий раз завершаем
				}
			}
		}
		return sb.toString();
	}

	private static String getDomainLastPart(String domain) {
		int lastIndex = domain.lastIndexOf(".");
		if (lastIndex == -1) {
			return domain;
		}
		return domain.substring(lastIndex + 1);
	}

	public static String getProtocol(String url) {
		if (url.startsWith(HTTPS_PREFIX)) return HTTPS_PREFIX;
		return HTTP_PREFIX;
	}

	public static boolean isURL(String value) {
		return (value.startsWith(HTTP_PREFIX) || value.startsWith(HTTPS_PREFIX) || value.startsWith("//")) && value.contains(".");
	}

	public static String removeImagesFromHTML(String html) {
		return html;
		/*
		def remove_base64_images_from_html(html)
  # remove data:image/png;base64, images
  base64regex = %r{(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?}
  imageregex = %r{data\s*:\s*image/[^\s;]+\s*;\s*base64\s*,\s*}
  html.gsub(/["']\s*#{imageregex}#{base64regex}\s*["']/, '""')
end
		 */
	}

	public static String getIP(String hostName) throws UnknownHostException {
		InetAddress inetAddress = InetAddress.getByName(hostName);
		return inetAddress.getHostAddress();
		/*
		byte[] ip = address.getAddress();
  for(byte b : ip){
    System.out.print(Integer.toString(((int)b)&0xFF)+".");
  }
		 */
	}

	public static String getHostName(String ip) throws UnknownHostException {
		InetAddress inetAddress = InetAddress.getByName(ip);
		return inetAddress.getCanonicalHostName();
	}

	public static String encodeURL(String url) {
		try {
			return URLEncoder.encode(url, ENCODING);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    /**
     * Используемая кодировка UTF-8
     *
     * @return вернёт декодированную строку encodedUrl
     */
    public static String decodeURL(String encodedUrl) {
        return URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8);

    }

    public static String getCookie(List<Cookie> cookieList, String name) {
        for (Cookie cookie : cookieList) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        throw new IllegalStateException("Not found cookie with name:"+name);
    }

    public static boolean hasCookie(List<Cookie> cookieList, String name) {
        for (Cookie cookie : cookieList) {
            if (cookie.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static Cookie setCookie(String cookieName, String value, HttpServletResponse response, int expirySec) {
        if (response != null) {
            Cookie cookie = new Cookie(cookieName, removeCRLF(value));
            cookie.setSecure(true);
            cookie.setMaxAge(expirySec);
            response.addCookie(cookie);
            return cookie;
        }
        return null;
    }

    public static String getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

    public static String getCookies(List<Cookie> cookieList) {
        return StringHelper.join(cookieList, cookie -> cookie.getName()+"="+cookie.getValue(), "; ");
    }

    /**
     * Удлаяем cookie
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private static String removeCRLF(String value)  {
        //http://stackoverflow.com/questions/16439618/how-to-fix-the-http-response-splitting-vulnerability-with-esapi
        //detect and remove any existent \r\n == %0D%0A == CRLF to prevent HTTP Response Splitting
        //Решение отсюда https://www.htbridge.com/vulnerability/http-response-splitting.html
        return value.replace("\\n\\r", "");
    }

    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    public static String getRequestInfoForLogging(HttpServletRequest request) {
        return "Request.Parameters:" + getRequestParameterMap(request) + " Request.Headers:" + getRequestHeaderParameters(request);
    }

    public static Map<String, String> getRequestParameterMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Enumeration en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            params.put(key, request.getParameter(key));
        }
        return params;
    }

    public static Map<String, String> getRequestHeaderParameters(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Enumeration en = request.getHeaderNames();
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            params.put(key, request.getHeader(key));
        }
        return params;
    }

    public static String getRequestForLog(HttpServletRequest request) {
        StringBuilder buf = new StringBuilder();
        Enumeration enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String key = (String) enums.nextElement();
            String[] values = request.getParameterValues(key);
            if (values != null && values.length > 0) {
                for (String value : values) {
                    if (buf.length() > 0) {
                        buf.append(URL_PARAMS_SEPARATOR);
                    }
                    buf.append(key).append("=").append(getCropValue(value));
                }
            }
        }
        return buf.toString();
    }

    private static String getCropValue(String value) {
        if (StringHelper.isNotEmpty(value) && value.length() > 1000) {
            value = value.substring(0, 1000);
            value += "... log string was trimmed";
        }
        return value;
    }

    /**
     * Устанавливаем Content-Disposition и Content-Type для скачиваемого файла
     */
    public static void setUTF8ContentDisposition(HttpServletResponse response, String fileName, String contentType) {
        //response.setHeader("Content-Disposition", getContentDisposition("attachment", fileName));
        response.setContentType(contentType);
        response.addHeader("Content-Type", contentType);
    }

    /**
     * Получить отформатированный заголовок Content-Disposition
     * Для браузеров, поддерживающих RFC-6266/RFC-5987
     * @param dispositionType inline, attachment...
     */
    private static String getContentDisposition(String dispositionType, String fileName) {
        try {
            return MessageFormat.format("{0};filename*={1}''''{2}",
                    dispositionType,
                    ENCODING,
                    URLEncoder.encode(fileName, ENCODING));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Incorrect encoding: " + ENCODING, e);
        }
    }

    public static void redirect(HttpServletResponse response, String path) {
        try {
            response.sendRedirect(path);
        } catch (IOException e) {
            throw new IllegalStateException("Error on redirect to path:" + path, e);
        }
    }

    public static List<Cookie> parseCookieString(String cookies) {
        List<Cookie> cookieList = new ArrayList<Cookie>();
        Pattern cookiePattern = Pattern.compile("([^=]+)=([^\\;]*);?\\s?");
        Matcher matcher = cookiePattern.matcher(cookies);
        while (matcher.find()) {
            int groupCount = matcher.groupCount();
            //System.out.println("matched: " + matcher.group(0));
            for (int groupIndex = 0; groupIndex <= groupCount; ++groupIndex) {
                //System.out.println("group[" + groupIndex + "]=" +
                String group = matcher.group(groupIndex);
                //);
            }
            String cookieKey = matcher.group(1);
            String cookieValue = matcher.group(2);
            Cookie cookie = new Cookie(cookieKey, cookieValue);
            cookieList.add(cookie);
        }
        return cookieList;
    }

    public static String getCookies(Cookie[] cookies) {
        if (cookies == null) {
            return "";
        }
        return getCookies(Arrays.asList(cookies));
    }

    /**
     * Если содержимое body слишком большое, то урезаем строку
     */
    public static String getRequestBodyForLog(HttpServletRequest request) {
        try {
            StringBuilder sb = new StringBuilder();
            request.getReader().lines().takeWhile(new Predicate<String>() {
                @Override
                public boolean test(String s) {
                    return sb.length() < 10000;
                }
            }).forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    sb.append(s);
                }
            });
            return sb.toString();
        } catch (IOException e) {
            throw new IllegalStateException("Error on request:" + request, e);
        }
    }

    /**
     * @return CATALINA_HOME
     */
    public static String getTomcatDir() {
        return System.getProperty("catalina.base");
    }

    public static String addTrailingSlash(String webContextPath) {
        return webContextPath.endsWith(WebHelper.URL_SEPARATOR) ? webContextPath : webContextPath + WebHelper.URL_SEPARATOR;
    }
}
