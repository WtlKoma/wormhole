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

package com.zergclan.wormhole.jdbc.core;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zergclan.wormhole.core.api.metadata.DataSourceMetadata;

import javax.sql.DataSource;

/**
 * Create factory for data source.
 */
public final class DataSourceCreator {

    /**
     * Create {@link DataSource}.
     *
     * @param dataSourceMetadata data source metadata
     * @return {@link DataSource}
     */
    public static DataSource create(final DataSourceMetadata dataSourceMetadata) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(dataSourceMetadata.getDriverClassName());
        config.setJdbcUrl(dataSourceMetadata.getJdbcUrl());
        config.setUsername(dataSourceMetadata.getUsername());
        config.setPassword(dataSourceMetadata.getPassword());
        return new HikariDataSource(config);
    }
}
