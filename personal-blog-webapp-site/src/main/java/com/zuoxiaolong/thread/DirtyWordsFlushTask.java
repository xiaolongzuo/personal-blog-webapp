package com.zuoxiaolong.thread;

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

import com.zuoxiaolong.util.DirtyWordsUtil;
import org.apache.log4j.Logger;

/**
 * @author 左潇龙
 * @since 5/28/2015 11:55 AM
 */
public class DirtyWordsFlushTask implements Runnable {

    private static final Logger logger = Logger.getLogger(FetchTask.class);

    @Override
    public void run() {
        while (true) {
            try {
                DirtyWordsUtil.flush();
                Thread.sleep(1000 * 60 * 60);
            } catch (Exception e) {
                logger.warn("fetch and generate failed ...", e);
                break;
            }
        }
    }
}
