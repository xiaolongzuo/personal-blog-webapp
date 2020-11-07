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

package com.zuoxiaolong.blog.controller;

import com.zuoxiaolong.blog.entity.AccessLog;
import com.zuoxiaolong.blog.service.AccessLogService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xiaolongzuo
 */
@RestController
@RequestMapping("/accessLog")
public class AccessLogController {

    @Resource
    private AccessLogService accessLogService;

    @RequestMapping("/save")
    public void save(@RequestBody AccessLog accessLog) {
        accessLogService.save(accessLog);
    }

}
