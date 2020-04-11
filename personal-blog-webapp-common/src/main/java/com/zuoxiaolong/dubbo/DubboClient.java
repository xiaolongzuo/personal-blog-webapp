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

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConsumerConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaolongzuo
 */
public class DubboClient {

    public static <T> T get(String applicationName, String registryUrl, String registryId, Class clazz) {
        ReferenceConfig<T> reference = new ReferenceConfig<>();
        ApplicationConfig applicationConfig = new ApplicationConfig(applicationName);
        applicationConfig.setQosEnable(false);
        reference.setApplication(applicationConfig);
        RegistryConfig registryConfig = new RegistryConfig(registryUrl);
        registryConfig.setId(registryId);
        reference.setRegistry(registryConfig);
        reference.setInterface(clazz);
        reference.setTimeout(10000);
        Map<String, String> parameters = reference.getParameters();
        if (parameters == null) {
            parameters = new HashMap<>();
        }
        parameters.put("unicast", "false");
        reference.setParameters(parameters);
        T service = reference.get();
        return service;
    }

}
