package com.zuoxiaolong.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.freemarker.Generators;

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
 * @since 5/7/2015 3:33 PM
 */
public class ConfigurationListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(ConfigurationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Configuration.init(servletContextEvent.getServletContext());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Generators.generate();
                        Thread.sleep(1000 * 60 * 10);
                    } catch (Exception e) {
                        logger.warn("generate failed ..." , e);
                        break;
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

}
