/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

package wirez.forms.backend.processors.impl;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.drools.workbench.models.datamodel.oracle.Annotation;
import org.livespark.formmodeler.renderer.backend.service.impl.processors.AbstractFieldAnnotationProcessor;
import org.livespark.formmodeler.renderer.service.TransformerContext;
import org.wirez.forms.meta.definition.ColorPicker;
import org.wirez.forms.model.ColorPickerFieldDefinition;
import org.wirez.forms.service.fieldProviders.ColorPickerFieldProvider;

/**
 * @author Pere Fernandez <pefernan@redhat.com>
 */
@Dependent
public class ColorPickerAnnotationProcessor extends AbstractFieldAnnotationProcessor<ColorPickerFieldDefinition, ColorPickerFieldProvider> {

    @Inject
    public ColorPickerAnnotationProcessor( ColorPickerFieldProvider fieldProvider ) {
        super( fieldProvider );
    }

    @Override
    protected Class<ColorPicker> getSupportedAnnotation() {
        return ColorPicker.class;
    }

    @Override
    protected void initField( ColorPickerFieldDefinition field, Annotation annotation, TransformerContext context ) {
        field.setDefaultValue( (String) annotation.getParameters().get( "defaultValue") );
    }
}
