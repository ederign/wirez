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

package org.wirez.client.widgets.session.toolbar.item;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Composite;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.uberfire.mvp.Command;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

// TODO: Multiple buttons with same id.

@Dependent
@Templated
public class ToolbarItemView extends Composite implements ToolbarItem.View {

    @Inject
    @DataField
    Button button;

    AbstractToolbarItem presenter;
    private Command command;

    @Override
    public void init(final AbstractToolbarItem presenter) {
        this.presenter = presenter;
    }

    @EventHandler( "button" )
    public void onButtonClick( final ClickEvent clickEvent ) {

        if ( null != command ) {
            command.execute();
        }

    }

    @Override
    public ToolbarItem.View setIcon(final IconType icon) {
        button.setIcon( icon );
        return this;
    }

    @Override
    public AbstractToolbarItem.View setCaption(final String caption) {
        button.setText( caption );
        return this;
    }

    @Override
    public ToolbarItem.View setTooltip(final String tooltip) {
        button.setTitle( tooltip );
        return this;
    }

    @Override
    public ToolbarItem.View setClickHandler(final Command command) {
        this.command = command;
        return this;
    }

    @Override
    public ToolbarItem.View setEnabled(final boolean enabled) {
        button.setEnabled( enabled );
        return this;
    }

    @Override
    public void destroy() {
        this.removeFromParent();
    }

}
