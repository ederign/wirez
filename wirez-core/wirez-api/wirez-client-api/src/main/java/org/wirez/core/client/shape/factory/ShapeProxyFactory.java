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
package org.wirez.core.client.shape.factory;

import org.wirez.core.client.shape.Shape;
import org.wirez.core.definition.shape.ShapeProxy;

/**
 * Factory for building proxied shapes.
 */
public interface ShapeProxyFactory<W, C, S extends Shape, P extends ShapeProxy<W>> extends ShapeFactory<W, C, S> {

    void addProxy(Class<?> clazz, P proxy);

}
