/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
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

package org.wirez.client.widgets.session.toolbar.impl;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.wirez.client.widgets.session.toolbar.ToolbarView;
import org.wirez.core.client.session.impl.DefaultCanvasFullSession;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
@Templated
public class ToolbarViewImpl extends Composite implements ToolbarView<DefaultCanvasFullSession> {

    @Inject
    @DataField
    private ButtonGroup mainGroup;

    AbstractToolbar presenter;

    @Override
    public void init(final AbstractToolbar presenter) {
        this.presenter = presenter;
    }

    @Override
    public ToolbarView<DefaultCanvasFullSession> addItem(final IsWidget w) {
        mainGroup.add( w );
        return this;
    }

    @Override
    public ToolbarView<DefaultCanvasFullSession> show() {
        this.setVisible( true );
        return this;
    }

    @Override
    public ToolbarView<DefaultCanvasFullSession> hide() {
        this.setVisible( false );
        return this;

    }


    @Override
    public ToolbarView<DefaultCanvasFullSession> clear() {
        mainGroup.clear();
        return this;
    }

    @Override
    public void destroy() {
        this.removeFromParent();
    }

}
