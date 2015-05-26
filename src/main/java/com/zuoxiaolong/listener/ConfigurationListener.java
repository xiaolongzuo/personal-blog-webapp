package com.zuoxiaolong.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.thread.Executor;
import com.zuoxiaolong.thread.FetchTask;

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
    	if (logger.isInfoEnabled()) {
			logger.info("will begin init configuration...");
		}
        Configuration.init(servletContextEvent.getServletContext());
        if (logger.isInfoEnabled()) {
			logger.info("init configuration success...");
		}
        if (!Configuration.isProductEnv()) {
//            ImageUtil.loadArticleImages();
//            Generators.generate();
        } else {
            if (logger.isInfoEnabled()) {
                logger.info("starting fetch and generate thread...");
            }
            Executor.executeTask(new FetchTask());
            if (logger.isInfoEnabled()) {
                logger.info("fetch and generate thread has been started...");
            }
            /* 由于登录功能，暂时不做百度推送
            if (logger.isInfoEnabled()) {
                logger.info("starting baidu push thread...");
            }
            Executor.executeTask(new BaiduPushTask());
            if (logger.isInfoEnabled()) {
                logger.info("baidu push thread has been started...");
            }
            */
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

}
