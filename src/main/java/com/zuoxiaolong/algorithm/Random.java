package com.zuoxiaolong.algorithm;

import java.util.ArrayList;
import java.util.List;

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
 * @since 2015年5月24日 下午11:18:03
 */
public abstract class Random {

	public static <E> List<E> random(List<E> sourceList, int number) {
		if (sourceList == null) {
			throw new IllegalArgumentException("sourceList is null!");
		}
		if (number > sourceList.size()) {
			number = sourceList.size();
		}
        List<E> randomList = new ArrayList<E>();
        for (int i = 0; i < number; i++) {
			randomList.add(sourceList.remove(new java.util.Random().nextInt(sourceList.size())));
		}
        return randomList;
	}
	
}
