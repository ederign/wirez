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
package org.wirez.client;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.ui.RootPanel;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.uberfire.client.mvp.ActivityManager;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.uberfire.client.workbench.widgets.menu.WorkbenchMenuBarPresenter;
import org.uberfire.mvp.impl.DefaultPlaceRequest;
import org.uberfire.workbench.model.menu.Menus;
import org.wirez.client.workbench.perspectives.AuthoringPerspective;
import org.wirez.client.workbench.perspectives.HomePerspective;
import org.wirez.client.workbench.perspectives.TestsPerspective;
import org.wirez.client.workbench.perspectives.WirezSandboxPerspective;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.uberfire.workbench.model.menu.MenuFactory.newTopLevelMenu;

/**
 * GWT's entry-point for wirez showcase.
 */
@EntryPoint
public class ShowcaseEntryPoint {

    private static Logger LOGGER = Logger.getLogger( ShowcaseEntryPoint.class.getName() );

    @Inject
    private PlaceManager placeManager;

    @Inject
    private WorkbenchMenuBarPresenter menubar;

    @Inject
    private ActivityManager activityManager;

    @Inject
    private ErrorPopupPresenter errorPopupPresenter;

    @AfterInitialization
    public void startApp() {

        setupGlobalErrorHandler();

        setupMenu();

        hideLoadingPopup();

        // Default perspective.
        placeManager.goTo( new DefaultPlaceRequest( HomePerspective.PERSPECTIVE_ID ) );
    }

    private void setupGlobalErrorHandler() {

        GWT.setUncaughtExceptionHandler( throwable -> {

            final String message = "Uncaught error on client side: " + throwable.getMessage();

            errorPopupPresenter.showMessage( message );

            log( Level.SEVERE, throwable.getMessage() );

        } );

    }

    private void setupMenu() {
        final Menus menus =
                newTopLevelMenu( "Home" ).respondsWith( () -> placeManager.goTo( new DefaultPlaceRequest( HomePerspective.PERSPECTIVE_ID ) ) ).endMenu()
                        .newTopLevelMenu( "Authoring" ).respondsWith( () -> placeManager.goTo( new DefaultPlaceRequest( AuthoringPerspective.PERSPECTIVE_ID ) ) ).endMenu()
                        .newTopLevelMenu( "Sandbox" ).respondsWith( () -> placeManager.goTo( new DefaultPlaceRequest( WirezSandboxPerspective.PERSPECTIVE_ID ) ) ).endMenu()
                        .newTopLevelMenu( "Tests" ).respondsWith( () -> placeManager.goTo( new DefaultPlaceRequest( TestsPerspective.PERSPECTIVE_ID ) ) ).endMenu()
                        .build();
        menubar.addMenus( menus );
    }

    // Fade out the "Loading application" pop-up
    private void hideLoadingPopup() {
        final Element e = RootPanel.get( "loading" ).getElement();

        new Animation() {

            @Override
            protected void onUpdate( double progress ) {
                e.getStyle().setOpacity( 1.0 - progress );
            }

            @Override
            protected void onComplete() {
                e.getStyle().setVisibility( Style.Visibility.HIDDEN );
            }
        }.run( 500 );
    }

    public static native void redirect( String url )/*-{
        $wnd.location = url;
    }-*/;

    private void log( final Level level, final String message ) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log( level, message );
        }
    }

}