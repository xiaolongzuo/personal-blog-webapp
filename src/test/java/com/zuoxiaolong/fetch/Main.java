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

/**
 * @author 左潇龙
 * @since 2015年5月10日 下午7:33:34
 */
package com.zuoxiaolong.fetch;

/**
 * @author zuoxiaolong
 *
 */
public class Main {

	public static void main(String[] args) {
		String aString = " com.state;   ";
		char[] chars = aString.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			System.out.println(chars[i]);
			System.out.println(chars[i] == '\r' || chars[i] == '\n' || chars[i] == '\t');
		}
	}
}
