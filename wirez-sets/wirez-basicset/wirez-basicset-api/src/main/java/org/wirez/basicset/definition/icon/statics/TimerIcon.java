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

package org.wirez.basicset.definition.icon.statics;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.basicset.definition.Categories;
import org.wirez.basicset.shape.proxy.icon.statics.StaticIconProxy;
import org.wirez.core.definition.annotation.Description;
import org.wirez.core.definition.annotation.Shape;
import org.wirez.core.definition.annotation.definition.Category;
import org.wirez.core.definition.annotation.definition.Definition;
import org.wirez.core.definition.annotation.definition.Labels;
import org.wirez.core.definition.annotation.definition.Title;
import org.wirez.core.definition.builder.Builder;
import org.wirez.core.factory.graph.NodeFactory;
import org.wirez.shapes.factory.BasicShapesFactory;
import org.wirez.shapes.proxy.icon.statics.Icons;

import java.util.HashSet;
import java.util.Set;

@Portable
@Bindable
@Definition( graphFactory = NodeFactory.class, builder = TimerIcon.TimerIconBuilder.class )
@Shape( factory = BasicShapesFactory.class, proxy = StaticIconProxy.class )
public class TimerIcon implements StaticIcon {

    @Category
    public static final transient String category = Categories.ICONS;

    @Title
    public static final transient String title = "Timer Icon";

    @Description
    public static final transient String description = "Timer Icon";
    
    @Labels
    private final Set<String> labels = new HashSet<String>() {{
        add( "all" );
    }};

    @NonPortable
    public static class TimerIconBuilder implements Builder<TimerIcon> {

        @Override
        public TimerIcon build() {
            return new TimerIcon();
        }

    }

    public TimerIcon() {

    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getLabels() {
        return labels;
    }

    @Override
    public Icons getIcon() {
        return Icons.TIMER;
    }
    
}
