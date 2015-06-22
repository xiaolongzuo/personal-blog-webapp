package com.zuoxiaolong.cache;

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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 左潇龙
 * @since 5/15/2015 5:11 PM
 */
public class ConcurrentHashMapCache extends AbstractCache {

    private int maxSize = 1000;

    private Map<String, Object> cache = new ConcurrentHashMap<>();

    private Map<String, Long> expiredCache = new ConcurrentHashMap<>();

    ConcurrentHashMapCache() {
    }

    ConcurrentHashMapCache(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public void set(String key, Object value, long expired) {
        if (cache.size() >= maxSize) {
            return;
        }
        cache.put(key, value);
        if (expired > 0) {
            expiredCache.put(key, System.currentTimeMillis() + expired);
        }
    }

    @Override
    public Object get(String key) {
        Long time = expiredCache.get(key);
        if (time == null || time > System.currentTimeMillis()) {
            return cache.get(key);
        } else {
            cache.remove(key);
            expiredCache.remove(key);
            return null;
        }
    }

}
