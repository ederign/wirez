/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.bpmn.api.property.general;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.annotation.property.DefaultValue;
import org.wirez.core.api.annotation.property.Value;
import org.wirez.core.api.definition.property.BaseProperty;
import org.wirez.core.api.definition.property.PropertyType;
import org.wirez.core.api.definition.property.type.StringType;

@Portable
public class FontColor extends BaseProperty {

    public static final String ID = "fontColor";

    public static final String DEFAULT_VALUE = "#000000";

    private String value = DEFAULT_VALUE;
    
    public FontColor() {
        super(ID, 
                "Font Color", "The font Color.", 
                false, false, true);
    }

    @Override
    public PropertyType getType() {
        return new StringType();
    }

    @DefaultValue
    public String getDefaultValue() {
        return DEFAULT_VALUE;
    }
    
    @Value
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
