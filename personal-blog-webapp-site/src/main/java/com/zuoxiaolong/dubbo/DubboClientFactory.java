/*
 * Copyright (C) 2019 the original author or authors.
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

package com.zuoxiaolong.dubbo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaolongzuo
 */
public class DubboClientFactory {

    private static final String APPLICATION_NAME = "personal-blog-web";

    private static final String REGISTRY_URL = "multicast://224.5.6.7:10086";

    private static final String REGISTRY_ID = "myregistry";

    private DubboClientFactory() {
    }

    private static final Map<Class<?>, Object> cache = new ConcurrentHashMap<>();

    public static <T> T getClient(Class<T> clazz) {
        T client = (T) cache.get(clazz);
        if (client == null) {
            client = DubboClient.get(APPLICATION_NAME, REGISTRY_URL, REGISTRY_ID, clazz);
            cache.putIfAbsent(clazz, client);
        }
        return (T) cache.get(clazz);
    }

}
