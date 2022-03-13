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
import lombok.RequiredArgsConstructor;

import java.util.Properties;

/**
 * Value range editor implemented of {@link FilterMetadata}.
 *
 */
@RequiredArgsConstructor
public final class ValueRangeEditorMetadata implements FilterMetadata {
    
    private static final FilterType FILTER_TYPE = FilterType.VALUE_RANGE;
    
    private final String taskIdentifier;
    
    private final int order;
    
    private final String sourceName;
    
    private final int startIndex;
    
    private final int endIndex;
    
    @Override
    public String getIdentifier() {
        return taskIdentifier + MarkConstant.SPACE + FILTER_TYPE.name() + MarkConstant.SPACE + order;
    }
    
    @Override
    public int getOrder() {
        return order;
    }
    
    @Override
    public FilterType getType() {
        return FILTER_TYPE;
    }
    
    /**
     * Builder for {@link ValueRangeEditorMetadata}.
     *
     * @param taskIdentifier task identifier
     * @param order order
     * @param props props
     * @return {@link ValueRangeEditorMetadata}
     */
    public static FilterMetadata builder(final String taskIdentifier, final int order, final Properties props) {
        String sourceName = props.getProperty("sourceName");
        Validator.notNull(sourceName, "error : build ValueRangeEditorMetadata failed sourceName in props can not be null, task identifier: [%s]", taskIdentifier);
        String endIndex = props.getProperty("endIndex");
        Validator.notNull(endIndex, "error : build ValueRangeEditorMetadata failed endIndex in props can not be null, task identifier: [%s]", taskIdentifier);
        String startIndex = props.getProperty("startIndex", "0");
        return new FilterBuilder(taskIdentifier, order, sourceName, Integer.parseInt(startIndex), Integer.parseInt(endIndex)).build();
    }
    
    @RequiredArgsConstructor
    private static class FilterBuilder {
        
        private final String taskIdentifier;
        
        private final int order;
        
        private final String sourceName;
    
        private final int startIndex;
    
        private final int endIndex;
        
        private ValueRangeEditorMetadata build() {
            return new ValueRangeEditorMetadata(taskIdentifier, order, sourceName, startIndex, endIndex);
        }
    }
}
