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

package org.wirez.client.workbench.screens;

import com.google.gwt.user.client.ui.IsWidget;
import org.uberfire.client.annotations.*;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.workbench.events.ChangeTitleWidgetEvent;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.uberfire.lifecycle.OnClose;
import org.uberfire.lifecycle.OnOpen;
import org.uberfire.lifecycle.OnStartup;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.workbench.model.menu.Menus;
import org.wirez.client.widgets.palette.accordion.Palette;
import org.wirez.client.workbench.event.CanvasScreenStateChangedEvent;
import org.wirez.core.client.ShapeSet;
import org.wirez.core.client.WirezClientManager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
@WorkbenchScreen(identifier = PaletteScreen.SCREEN_ID )
public class PaletteScreen {

    public static final String SCREEN_ID = "PaletteScreen";

    @Inject
    Palette palette;
    
    @Inject
    ErrorPopupPresenter errorPopupPresenter;
    
    @Inject
    PlaceManager placeManager;
    
    @Inject
    WirezClientManager wirezClientManager;

    @Inject
    Event<ChangeTitleWidgetEvent> changeTitleNotification;
    
    private PlaceRequest placeRequest;
    private String shapeSetId;
    
    @PostConstruct
    public void init() {
    }

    @OnStartup
    public void onStartup(final PlaceRequest placeRequest) {
        this.placeRequest = placeRequest;
        this.shapeSetId = placeRequest.getParameter( "shapeSetId", "" );
        open();
    }
    
    @OnOpen
    public void onOpen() {
        open();
    }

    @OnClose
    public void onClose() {
        palette.showNoCanvasState();
    }

    private void open() {
        if (shapeSetId.trim().length() > 0) {
            palette.show(shapeSetId);
        } else {
            palette.showNoCanvasState();
        }
    }

    @WorkbenchMenu
    public Menus getMenu() {
        return null;
    }

    private void showError(final String message) {
        errorPopupPresenter.showMessage(message);
    }

    @WorkbenchPartTitle
    public String getTitle() {
        return "Palette";
    }

    @WorkbenchPartView
    public IsWidget getWidget() {
        return palette.asWidget();
    }
    
    @WorkbenchContextId
    public String getMyContextRef() {
        return "wirezPaletteScreenContext";
    }

    void onCanvasScreenStateChanged(@Observes CanvasScreenStateChangedEvent canvasScreenStateChangedEvent) {
        checkNotNull("canvasScreenStateChangedEvent", canvasScreenStateChangedEvent);
        final CanvasScreen.CanvasScreenState state = canvasScreenStateChangedEvent.getState();
        final ShapeSet shapeSet = canvasScreenStateChangedEvent.getCanvasHandler().getSettings().getShapeSet();
        
        if (CanvasScreen.CanvasScreenState.ACTIVE.equals(state)) {
            
            // Open the palette with the given shape set.
            changeTitleNotification.fire(new ChangeTitleWidgetEvent(placeRequest, shapeSet.getName() + " Palette"));
            this.shapeSetId = shapeSet.getId();
            open();
            
        } else if (this.shapeSetId != null && shapeSetId.equals(shapeSet.getId())){
            
            // Close the current palette.
            changeTitleNotification.fire(new ChangeTitleWidgetEvent(placeRequest, "Palette"));
            this.shapeSetId = null;
            open();
        }
        
    }
}