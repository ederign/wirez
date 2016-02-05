package org.wirez.client.widgets.wizard.screen.load;

import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.mvp.impl.DefaultPlaceRequest;
import org.wirez.client.widgets.event.LoadDiagramEvent;
import org.wirez.client.widgets.wizard.BaseWizardScreen;
import org.wirez.client.widgets.wizard.CanvasWizard;
import org.wirez.client.widgets.wizard.CanvasWizardScreen;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.Settings;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.ShapeSet;
import org.wirez.core.client.service.ClientDiagramServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.util.Logger;
import org.wirez.core.client.util.ShapeUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Dependent
public class LoadDiagramWizardScreen extends BaseWizardScreen implements CanvasWizardScreen {

    public interface View extends UberView<LoadDiagramWizardScreen> {
        
        View showEmpty();
        
        View add(String title, String path, SafeUri thumbUri);
        
        View clear();
        
    }

    ShapeManager shapeManager;
    ClientDiagramServices clientDiagramServices;
    Event<LoadDiagramEvent> loadDiagramEventEvent;
    View view;
    
    private String selectedPath = null;

    @Inject
    public LoadDiagramWizardScreen(final ShapeManager shapeManager,
                                   final ClientDiagramServices clientDiagramServices,
                                   final Event<LoadDiagramEvent> loadDiagramEventEvent, 
                                   final View view) {
        this.shapeManager = shapeManager;
        this.clientDiagramServices = clientDiagramServices;
        this.loadDiagramEventEvent = loadDiagramEventEvent;
        this.view = view;
    }

    @PostConstruct
    public void init() {
        view.init(this);
    }
    
    @Override
    public String getNextButtonText() {
        return "Load";
    }

    @Override
    protected void doShow() {
        super.doShow();

        clear();

        wizard.setNextButtonEnabled(false);
        
        clientDiagramServices.search("", new ServiceCallback<Collection<Diagram>>() {
            @Override
            public void onSuccess(final Collection<Diagram> items) {
                
                if ( null == items || items.isEmpty() ) {
                    
                    view.showEmpty();
                    
                } else {
                    
                    for (final Diagram diagram : items) {
                        addEntry( diagram );
                    }
                    
                }
                
                
            }

            @Override
            public void onError(final ClientRuntimeError error) {
                Logger.logError( error );
            }
        });
    }
    
    private void addEntry(final Diagram diagram) {
        assert diagram != null;
        Settings settings = diagram.getSettings();
        final Collection<ShapeSet> shapeSets = shapeManager.getShapeSets();
        final SafeUri thumbUri = ShapeUtils.getShapeSet(shapeSets, settings.getShapeSetId()).getThumbnailUri();
        view.add( settings.getTitle(), settings.getPath(), thumbUri );
    }

    @Override
    public Callback getCallback() {
        return new Callback() {
            
            @Override
            public void onNextButtonClick() {

                // Clear to home screen.
                wizard.clear();

                // Fire the load diagram event for others.
                loadDiagramEventEvent.fire(new LoadDiagramEvent( selectedPath ));
                
            }

            @Override
            public void onBackButtonClick() {
                wizard.clear();
            }
            
        };
    }
    
    public void clear() {
        selectedPath = null;
        view.clear();
    }
    
    void onItemClick(final String path) {
        this.selectedPath = path;
        
        if ( selectedPath != null ) {
            
            wizard.setNextButtonEnabled(true);
            
        } else {

            wizard.setNextButtonEnabled(false);
            
        }
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }
    
}