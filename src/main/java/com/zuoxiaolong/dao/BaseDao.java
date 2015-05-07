package com.zuoxiaolong.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.zuoxiaolong.jdbc.ConnectionFactory;

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
 * @since 5/7/2015 11:44 AM
 */
public abstract class BaseDao {

    private static final Logger logger = Logger.getLogger(BaseDao.class);

    protected static void info(String message) {
        if (logger.isInfoEnabled()) {
            logger.info(message);
        }
    }

    protected static void warn(String message) {
        warn(message, null);
    }

    protected static void warn(String message, Exception e) {
        if (e != null) {
            logger.warn(message, e);
        } else {
            logger.warn(message);
        }
    }

    protected static void error(String message) {
        error(message, null);
    }

    protected static void error(String message, Exception e) {
        if (e != null) {
            logger.error(message, e);
        } else {
            logger.error(message);
        }
    }

    protected static <T> T execute(Operation<T> operation) {
        Connection connection = null;
        try {
            connection = ConnectionFactory.getConnection();
            connection.setReadOnly(true);
            return operation.doInConnection(connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected static <T> T execute(TransactionalOperation<T> transactionalOperation) {
        Connection connection = null;
        try {
            connection = ConnectionFactory.getConnection();
            connection.setAutoCommit(false);
            T result = transactionalOperation.doInConnection(connection);
            connection.commit();
            return result;
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    throw new RuntimeException(e1);
                }
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

interface TransactionalOperation<T> {

    public T doInConnection(Connection connection);

}

interface Operation<T> {

    public T doInConnection(Connection connection);

}