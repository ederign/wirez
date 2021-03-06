/*
 * Copyright 2016  Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.bpmn.definition.property.dataio;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.bpmn.definition.BPMNProperty;
import org.wirez.core.definition.annotation.Description;
import org.wirez.core.definition.annotation.property.Caption;
import org.wirez.core.definition.annotation.property.DefaultValue;
import org.wirez.core.definition.annotation.property.Optional;
import org.wirez.core.definition.annotation.property.Property;
import org.wirez.core.definition.annotation.property.ReadOnly;
import org.wirez.core.definition.annotation.property.Type;
import org.wirez.core.definition.annotation.property.Value;
import org.wirez.core.definition.property.PropertyType;
import org.wirez.core.definition.property.type.StringType;

/*
    TODO @Jeremy: 
    - Renamed from Assignments to TAssignments due to this property produces marshalling exceptions.
    - If this class is named Assignments, by default the BPMN marshallers assign the oryx id "assigments" for this property, wich
        is fine, but then it produces getting into current jbpm marshaller code that is not working here.
    - To reproduce the error rename this class and references to Assignments again and run
        the test BPMNDiagramMarshallerTest#testMarshallEvaluation -> it produces a null pointer exception.
        Note: When debugging I can see the problem is due to there is not IOSpecification for the task instance, which seems
         a must when the task contains assignments.
*/

@Portable
@Bindable
@Property
public class TAssignments implements BPMNProperty {

    @Caption
    public static final transient String caption = "Assignments";

    @Description
    public static final transient String description = "Assignments for the Activity";

    @ReadOnly
    public static final Boolean readOnly = false;

    @Optional
    public static final Boolean optional = false;

    @Type
    public static final PropertyType type = new StringType();

    @DefaultValue
    public static final String defaultValue = "";

    @Value
    private String value = defaultValue;

    public TAssignments() {
    }

    public TAssignments( final String value ) {
        this.value = value;
    }

    public String getCaption() {
        return caption;
    }

    public String getDescription() {
        return description;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public boolean isOptional() {
        return optional;
    }

    public PropertyType getType() {
        return type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
