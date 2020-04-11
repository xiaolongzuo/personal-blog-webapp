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

package com.zuoxiaolong.test;

import com.zuoxiaolong.blog.client.TagDubboService;
import com.zuoxiaolong.dubbo.DubboClient;
import org.junit.Test;

/**
 * @author xiaolongzuo
 */
public class DubboClientTest {

    @Test
    public void testDubboClient() throws InterruptedException {
        TagDubboService tagDubboService = DubboClient.get("personal-client", "multicast://224.5.6.7:10086", "myregistry", TagDubboService.class);
        System.out.println(tagDubboService.getHotTags());
    }
}
