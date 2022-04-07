/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zergclan.wormhole.plugin.mysql.xsql;

import com.zergclan.wormhole.plugin.mysql.xsql.parameter.ParameterHandler;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.List;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * execute sql statement.
 */
public class SqlExecutor {

    private static final Map<String, ParameterHandler> PARAMETER_HANDLER_CACHE = new ConcurrentHashMap<>(8);

    private final JdbcTemplate jdbcTemplate;

    public SqlExecutor(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * query.
     * @param querySql {@link String}
     * @param selectData {@link Collection}
     * @return List
     * @throws SQLException  Exception
     */
    public List<Map<String, Object>> query(final String querySql, final Object selectData) throws SQLException {
        List<Map<String, Object>> result = new LinkedList<>();
        try (PreparedStatement ps = prepareSql(querySql, selectData)) {
            System.out.println("----------------------query-------------------:" + ps);
            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> map = new LinkedHashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    map.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
                }
                result.add(map);
            }
        }
        return result;
    }

    /**
     * execute.
     * @param executeSql  {@link String}
     * @param executeData {@link Collection}
     * @throws SQLException  Exception
     */
    public void execute(final String executeSql, final Object executeData) throws SQLException {
        try (PreparedStatement ps = prepareSql(executeSql, executeData)) {
            System.out.println("----------------------execute-------------------:" + ps);
            ps.execute();
        }
    }

    /**
     * prepare sql.
     *
     * @param sql {@link String}
     * @param params {@link Object}
     * @return PreparedStatement
     * @throws SQLException notNull
     */
    private PreparedStatement prepareSql(final String sql, final Object params) throws SQLException {

        ParameterHandler parameterHandler = PARAMETER_HANDLER_CACHE.get(sql);
        if (parameterHandler == null) {
            ParameterPlanner parameterPlanner = new ParameterPlanner();
            parameterHandler = parameterPlanner.plan(sql, params);
            PARAMETER_HANDLER_CACHE.put(sql, parameterHandler);
        }
        String sql2 = parameterHandler.getSql(params);
        PreparedStatement ps = jdbcTemplate.getDataSource().getConnection().prepareStatement(sql2);
        parameterHandler.setParameters(ps, params);
        return ps;
    }
}
