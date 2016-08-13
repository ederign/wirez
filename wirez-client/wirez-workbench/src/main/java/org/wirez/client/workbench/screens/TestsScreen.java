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
import org.uberfire.lifecycle.OnClose;
import org.uberfire.lifecycle.OnOpen;
import org.uberfire.lifecycle.OnStartup;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.workbench.model.menu.Menus;
import org.wirez.client.widgets.help.carousel.HelpCarouselPresenter;
import org.wirez.client.workbench.perspectives.WirezSandboxPerspective;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
@WorkbenchScreen(identifier = TestsScreen.SCREEN_ID )
public class TestsScreen {

    public static final String SCREEN_ID = "TestsScreen";
    public static final int WIDTH = WirezSandboxPerspective.EAST_PANEL_WIDTH;
    public static final int HEIGHT = 200;

    @Inject
    HelpCarouselPresenter helpCarouselPresenter;
    

    @PostConstruct
    public void init() {
        
    }

    @OnStartup
    public void onStartup(final PlaceRequest placeRequest) {
        show();
    }
    
    @OnOpen
    public void onOpen() {
    }

    @OnClose
    public void onClose() {
    }

    public void show(  ) {

    }

    @WorkbenchMenu
    public Menus getMenu() {
        return null;
    }

    @WorkbenchPartTitle
    public String getTitle() {
        return "Tests Screen";
    }

    @WorkbenchPartView
    public IsWidget getWidget() {
        return helpCarouselPresenter.asWidget();
    }
    
    @WorkbenchContextId
    public String getMyContextRef() {
        return "testsScreenContext";
    }


}
