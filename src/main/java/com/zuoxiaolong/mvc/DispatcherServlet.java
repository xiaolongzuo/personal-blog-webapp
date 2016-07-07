package com.zuoxiaolong.mvc;

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

import com.zuoxiaolong.servlet.Servlet;
import com.zuoxiaolong.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 6/16/2015 10:54 AM
 */
public class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = -2086454661760376040L;
	
	private Map<String, Servlet> mapping;

    @Override
    public void init() throws ServletException {
        super.init();
        mapping = Scanner.scan();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        String realRequestUri = requestUri.substring(request.getContextPath().length(), requestUri.length());
        Servlet servlet = mapping.get(realRequestUri);
        while (servlet == null) {
            if (realRequestUri.startsWith("/")) {
                servlet = mapping.get(StringUtil.replaceStartSlant(realRequestUri));
            } else {
                throw new RuntimeException("unknown request mapping.");
            }
        }
        servlet.execute(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


}
