package com.zuoxiaolong.model;

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
 * @since 15/6/26 01:06
 */
public enum Status {
    published {
        @Override
        public int getIntValue() {
            return 1;
        }
    },draft {
        @Override
        public int getIntValue() {
            return 0;
        }
    };

    public abstract int getIntValue();

    public static Status valueOf(int value) {
        if (value == 0) {
            return draft;
        }
        if (value == 1) {
            return published;
        }
        throw new RuntimeException("unknown value");
    }

}
