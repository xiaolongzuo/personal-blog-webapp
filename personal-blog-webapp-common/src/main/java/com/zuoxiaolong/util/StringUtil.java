package com.zuoxiaolong.util;

/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Pattern;

/**
 * @author 左潇龙
 * @since 2015年5月30日 下午8:05:03
 */
public abstract class StringUtil {
	
	private static final String chinesePattern = "[\u4e00-\u9fa5]{1,1}";

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isEmptyHtml(String s) {
        s = JsoupUtil.getText(s);
        char[] cs = s.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] == 160 || cs[i] == 32) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

	public static String replaceSlants(String url) {
		return url.replaceAll("/+", "/");
	}

	public static String escapeHtml(String html) {
		StringBuffer stringBuffer = new StringBuffer();
        char[] chars = html.toCharArray();
        for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '\'') {
				stringBuffer.append("\\'");
			} else if (chars[i] == '\n') {
				stringBuffer.append("\\n");
			} else if (chars[i] == '\r') {
				stringBuffer.append("\\r");
			} else {
				stringBuffer.append(chars[i]);
			}
		}
        return stringBuffer.toString();
	}

	public static String urlEncode(String param) {
		try {
			return URLEncoder.encode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String urlDecode(String param) {
		try {
			return URLDecoder.decode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String substring(String source, int length) {
    	if (source == null) {
    		return null;
		}
		char[] chars = source.toCharArray();
		int realLength = length * 2;
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0, len = 0; i < chars.length; i++) {
			if (Pattern.matches(chinesePattern, String.valueOf(chars[i]))) {
				len += 2;
			} else {
				len++;
			}
			if (len <= realLength) {
				stringBuffer.append(chars[i]);
			} else {
				break;
			}
		}
		return stringBuffer.toString();
	}
	
	public static String replaceStartSlant(String s) {
        while (s.startsWith("/")) {
            s = s.substring(1);
        }
        return s;
    }

	public static String replace(String source, String prefix, String suffix, String prefixReplace, String suffixReplace) {
		char[] chars = source.toCharArray();
		char[] prefixChars = prefix.toCharArray();
		char[] suffixChars = suffix.toCharArray();
		StringBuffer result = new StringBuffer();
		int index = 0;
		boolean findPrefix = false;
		boolean findSuffix = false;
		int prefixIndex = -1;
		int suffixIndex = -1;
		out:
		for (int i = 0; i < chars.length; i++) {
			if ((i + prefixChars.length - 1) < chars.length) {
				for (int j = 0; j < prefixChars.length; j++) {
					if (chars[i + j] != prefixChars[j]) {
						break;
					}
					if (j == prefixChars.length - 1) {
						findPrefix = true;
						prefixIndex = i;
						i += prefixChars.length;
						continue out;
					}
				}
			}
			if ((i + suffixChars.length - 1) < chars.length) {
				for (int j = 0; j < suffixChars.length; j++) {
					if (chars[i + j] != suffixChars[j]) {
						break;
					}
					if (j == suffixChars.length - 1) {
						findSuffix = true;
						suffixIndex = i;
						i += prefixChars.length;
					}
				}
			}
			if (findPrefix && findSuffix) {
				result.append(source.substring(index, prefixIndex));
				result.append(prefixReplace);
				result.append(source.substring(prefixIndex + prefixChars.length, suffixIndex));
				result.append(suffixReplace);
				index = suffixIndex + suffixChars.length;
				findPrefix = false;
				findSuffix = false;
			}
		}
		if (index < source.length()) {
			result.append(source.substring(index, source.length()));
		}
		return result.toString();
	}

}
