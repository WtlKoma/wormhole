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

package com.zergclan.wormhole.core.metadata.filter.precise.editor;

import com.zergclan.wormhole.common.constant.MarkConstant;
import com.zergclan.wormhole.common.util.Validator;
import com.zergclan.wormhole.core.metadata.filter.FilterMetadata;
import com.zergclan.wormhole.core.metadata.filter.FilterType;
import com.zergclan.wormhole.core.metadata.node.DataNodeTypeMetadata;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Properties;

/**
 * Fixed node editor implemented of {@link FilterMetadata}.
 */
@RequiredArgsConstructor
public final class FixedNodeEditorMetadata implements FilterMetadata {

    private static final FilterType FILTER_TYPE = FilterType.FIXED_NODE;

    private final String taskIdentifier;

    @Getter
    private final int order;

    private final String sourceName;

    private final String defaultValue;

    private final DataNodeTypeMetadata.DataType dataType;

    @Override
    public String getIdentifier() {
        return taskIdentifier + MarkConstant.SPACE + FILTER_TYPE.name() + MarkConstant.SPACE + order;
    }

    @Override
    public FilterType getType() {
        return FILTER_TYPE;
    }

    /**
     * Builder for {@link FixedNodeEditorMetadata}.
     *
     * @param taskIdentifier task identifier
     * @param order order
     * @param props props
     * @return {@link FixedNodeEditorMetadata}
     */
    public static FixedNodeEditorMetadata builder(final String taskIdentifier, final int order, final Properties props) {
        String sourceName = props.getProperty("sourceName");
        Validator.notNull(sourceName, "error : build FixedNodeEditorMetadata failed sourceName in props can not be null, task identifier: [%s]", taskIdentifier);
        String defaultValue = props.getProperty("defaultValue");
        Validator.notNull(defaultValue, "error : build FixedNodeEditorMetadata failed defaultValue in props can not be null, task identifier: [%s]", taskIdentifier);
        String dataType = props.getProperty("dataType");
        Validator.notNull(dataType, "error : build FixedNodeEditorMetadata failed dataType in props can not be null, task identifier: [%s]", taskIdentifier);
        return new FixedNodeEditorMetadata.FilterBuilder(taskIdentifier, order, sourceName, defaultValue, DataNodeTypeMetadata.DataType.valueOf(dataType)).build();
    }

    @RequiredArgsConstructor
    private static class FilterBuilder {

        private final String taskIdentifier;

        private final int order;

        private final String sourceName;

        private final String defaultValue;

        private final DataNodeTypeMetadata.DataType dataType;

        private FixedNodeEditorMetadata build() {
            return new FixedNodeEditorMetadata(taskIdentifier, order, sourceName, defaultValue, dataType);
        }
    }
}
