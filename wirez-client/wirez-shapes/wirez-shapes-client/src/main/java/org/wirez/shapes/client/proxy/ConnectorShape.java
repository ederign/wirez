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

package org.wirez.shapes.client.proxy;

import org.wirez.shapes.client.view.ConnectorView;
import org.wirez.shapes.proxy.ConnectorProxy;

public class ConnectorShape<W> extends AbstractProxyConnector<W, ConnectorView, ConnectorProxy<W>> {


    public ConnectorShape( final ConnectorView view,
                           final ConnectorProxy<W> proxy ) {
        super( view, proxy );
    }

    @Override
    public String toString() {
        return "ConnectorShape{}";
    }
    
}
