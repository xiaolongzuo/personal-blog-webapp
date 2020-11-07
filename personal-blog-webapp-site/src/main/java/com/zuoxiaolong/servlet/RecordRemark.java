package com.zuoxiaolong.servlet;

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

import com.zuoxiaolong.client.HttpClient;
import com.zuoxiaolong.client.HttpUriEnums;
import com.zuoxiaolong.dao.*;
import com.zuoxiaolong.generator.Generators;
import com.zuoxiaolong.orm.DaoFactory;
import com.zuoxiaolong.util.HttpUtil;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author 左潇龙
 * @since 6/19/2015 6:18 PM
 */
public class RecordRemark extends AbstractServlet {

    @Override
    protected void service() throws ServletException, IOException {
        Integer recordId = Integer.valueOf(getRequest().getParameter("recordId"));
        String ip = HttpUtil.getVisitorIp(getRequest());
        String username = getUsername();
        if (HttpClient.get(Boolean.class, HttpUriEnums.RECORD_ID_VISITOR_IP_EXISTS, new String[]{"recordId", "ip", "username"}, recordId, ip, username)) {
            writeText("exists");
            if (logger.isInfoEnabled()) {
                logger.info(ip + " has remarked...");
            }
            return ;
        } else {
            HttpClient.get(HttpUriEnums.RECORD_ID_VISITOR_IP_SAVE, new String[]{"recordId", "ip", "username"}, recordId, ip, username);
        }
        boolean result = DaoFactory.getDao(RecordDao.class).updateGoodTimes(recordId);
        if (!result) {
            logger.error("updateCount error!");
            return;
        }
        if (result && logger.isInfoEnabled()) {
            logger.info("updateCount success!");
        }
        Generators.generateRecord(recordId);
        writeText("success");
    }

}
