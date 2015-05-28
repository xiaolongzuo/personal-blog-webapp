package com.zuoxiaolong.util;

import com.zuoxiaolong.config.Configuration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
 * @since 5/28/2015 11:11 AM
 */
public abstract class DirtyWordsUtil {

    private static final List<String> dirtyWords = new CopyOnWriteArrayList();

    public static boolean isDirtyWords(String words) {
        for (String dirtyWord : dirtyWords) {
            if (words.contains(dirtyWord)) {
                return true;
            }
        }
        return false;
    }

    private static int skip = 0;

    public static void flush() {
        try {
            List<String> temp = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(Configuration.getClasspathFile("dirty.words.txt")));
            for (int i = 0; i < skip; i++) {
                reader.readLine();
            }
            String line;
            while ((line = reader.readLine()) != null) {
                skip++;
                temp.add(line.trim());
            }
            reader.close();
            dirtyWords.addAll(temp);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        flush();
    }

}
