/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Разные полезные функции по работе со строками
 */
public class StringHelper {

    public static final String END_LINE = "\n";
    public static final String END_LINE_R = "\r\n";
    public static final String WHITE_SPACE = " ";
    private static final String NUMBERS = "0123456789";

    public static String toString(OptionalDouble value) {
        return value.isPresent() ? toString(value.getAsDouble()) : "";
    }


    public static String toString(Double value) {
        return toString(value, 2);
    }

    public static String toString(Double value, int afterDotCount) {
        if (value == null) {
            return "";
        }
        return String.format("%." + afterDotCount + "f", value);
    }

    public static boolean isEmpty(String value) {
		return value == null || value.equals("");
	}

	public static boolean isNotEmpty(String value) {
		return value != null && !value.equals("");
	}

	public static String firstNotEmpty(String... values) {
		for (String value : values) {
			if (StringHelper.isNotEmpty(value)) {
				return value;
			}
		}
		return null;
	}

	/**
	 * Если строка длиннее чем length, то обрезаем
	 */
	public static String cut(String value, int length) {
		if (value.length() < length) {
			return value;
		}
		return value.substring(0, length - 1);
	}

	public static String getStringBetween(String s, String after, String before) {
		int start = s.indexOf(after);
		if (start > -1) {
			int index = start + after.length();
			int end = s.indexOf(before, index);
			if (end > -1) {
				return s.substring(index, end);
			}
		}
		return "";
	}

    public static String getStringBetweenNotEmpty(String s, String after, String before) {
        String text = getStringBetween(s, after, before);
        if (StringHelper.isEmpty(text)) {
            throw new IllegalStateException("Nothing found between "+after+" and " + before + "in text: "+s);
        }
        return text;
    }

	public static List<String> getStringsBetween(String s, String after, String before) {
		List<String> result = new ArrayList<String>();
		int start = s.indexOf(after);
		while (start > -1) {
			int index = start + after.length();
			int end = s.indexOf(before, index);
			if (end > -1) {
				result.add(s.substring(index, end));
				start = s.indexOf(after, end);
			} else {
				start = -1;
			}
		}
		return result;
	}

	public static String getStringAfter(String s, String after) {
		int start = s.indexOf(after);
		if (start > -1) {
			return s.substring(start + after.length());
		}
		return "";
	}

	public static String getLastStringAfter(String s, String after) {
		int start = s.lastIndexOf(after);
		if (start > -1) {
			return s.substring(start + after.length());
		}
		return "";
	}

	public static String getStringBefore(String s, String before) {
		int index = s.indexOf(before);
		if (index > -1) {
			return s.substring(0, index);
		}
		return "";
	}

    public static String getStringBeforeNotEmpty(String s, String before) {
        String result = getStringBefore(s, before);
        if (isEmpty(result)) {
            throw new IllegalStateException("Not found before string:"+before+" in string:"+s);
        }
        return result;
    }

	public static List<String> getByPattern(Pattern pattern, String source) {
		List<String> values = new ArrayList<String>();
		Matcher matcher = pattern.matcher(source);
		while (matcher.find()) {
			String value = matcher.group(1).trim();
			values.add(value);
		}
		return values;
	}

	public static List<String> getByPattern(Pattern pattern, List<String> sourceList) {
		List<String> values = new ArrayList<String>();
		for (String source : sourceList) {
			String value = getFirstByPattern(pattern, source);
			if (StringHelper.isNotEmpty(value)) {
				values.add(value);
			}
		}
		return values;
	}

	public static String getFirstByPattern(Pattern pattern, String source) {
		Matcher matcher = pattern.matcher(source);
		if (matcher.find()) {
			return matcher.group(1).trim();
		}
		return "";
	}

	public static boolean matchByPattern(Pattern pattern, String source) {
		return pattern.matcher(source).find();
	}

	public static String md5(String st, String encoding) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			byte[] data = st.getBytes(encoding);
			m.update(data, 0, data.length);
			BigInteger i = new BigInteger(1, m.digest());
			return String.format("%1$032X", i);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public static String getOnlyNumbers(String columnText) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < columnText.length(); i++) {
			char c = columnText.charAt(i);
			if (NUMBERS.indexOf(c) > -1) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String getFirstNumber(String text) {
		StringBuilder sb = new StringBuilder();
		if (isNotEmpty(text)) {
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				if (NUMBERS.indexOf(c) > -1) {
					sb.append(c);
				} else {
					break;
				}
			}
		}
		return sb.toString();
	}

	/**
	 * @return строка, находящаяся между after и before в строке s, включая after и before.
	 * Если не найдена одна из строк after или before, то пустая строка
	 */
	public static String getStringBetweenInclusive(String s, String after, String before) {
		int start = s.indexOf(after);
		if (start > -1) {
			int end = s.indexOf(before, start);
			if (end > -1) {
				return s.substring(start, end);
			}
		}
		return "";
	}

	/**
	 * Пример, при формате: Hello %s My World %s
	 * и входной строке Hello Darling My World is my
	 * результат будет: [Darling, is my]
	 * при входной строке Hello Darling My World
	 * результат будет [Darling]
	 */
	public static List<String> parse(String s, String format) {
		List<String> result = new ArrayList<>();
		String[] formatValues = format.split("%s");
		for (String formatValue : formatValues) {
			String value = getStringBefore(s, formatValue);
			if (isNotEmpty(value)) {
				result.add(value);
			}
			s = getStringAfter(s, formatValue);
		}
		if (isNotEmpty(s)) {
			result.add(s);
		}
		return result;
	}

    public static List<String> lowerCaseList(Collection<String> words) {
        return words.stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    public static <T> String join(Collection<T> collection, Function<T, String> transformFunction, String separator) {
        StringBuilder sb = new StringBuilder();
        for (T element : collection) {
            if (sb.length() > 0) {
                sb.append(separator);
            }
            sb.append(transformFunction.apply(element));
        }
        return sb.toString();
    }

    public static String join(Collection<String> collection, String separator) {
        return join(collection, s -> s, separator);
    }

    public static <T> String join(Collection<T> collection, Function<T, String> transformFunction) {
        return join(collection, transformFunction, ",");
    }

    public static String replace(String template, String placeholder, String replacement) {
        if (template == null) {
            return null;
        }
        int lastLoc = 0;
        int loc = template.indexOf(placeholder, lastLoc);
        if (loc == -1) return template;
        StringBuilder buf = new StringBuilder();
        while (loc >= 0) {
            buf.append(template, lastLoc, loc);
            buf.append(replacement);
            lastLoc = loc + placeholder.length();
            loc = template.indexOf(placeholder, lastLoc);
        }
        buf.append(template.substring(lastLoc));
        return buf.toString();
    }

    /**
     * @return Строку, представляющую число value + количество нулей до длины digitsNumber
     */
    public static String getIntValueWithDigitsNumbers(int value, int digitsNumber) {
        StringBuilder stValue = new StringBuilder(Integer.toString(value));
        while (stValue.length() < digitsNumber) {
            stValue.insert(0, "0");
        }
        return stValue.toString();
    }

    public static String spintax(String str) {
        String pat = "\\{[^{}]*\\}";
        Pattern ma;
        ma = Pattern.compile(pat);
        Matcher mat = ma.matcher(str);
        while(mat.find())
        {
            String segono = str.substring(mat.start() + 1,mat.end() - 1);
            String[] choies = segono.split("\\|",-1);
            str = str.substring(0, mat.start()) + choies[RandomUtils.nextInt(choies.length-1)] + str.substring(mat.start()+mat.group().length());
            mat = ma.matcher(str);
        }
        return str;
    }

    public static int index(String[] array, String st) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(st)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Кодирует строку в строку base64
     */
    public static String encodeBase64(String text) {
        try {
            return Base64.getEncoder().withoutPadding().encodeToString(text.getBytes());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Если строка заканчивается на один из suffix
     */
    public static boolean endsWithAny(String st, String... suffixArray) {
        return Stream.of(suffixArray).anyMatch(st::endsWith);
    }

}
