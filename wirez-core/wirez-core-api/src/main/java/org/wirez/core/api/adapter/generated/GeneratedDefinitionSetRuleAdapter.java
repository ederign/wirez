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

package org.wirez.core.api.adapter.generated;

import org.wirez.core.api.adapter.DefinitionSetRuleAdapter;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.rule.Rule;

import java.util.Collection;

public abstract class GeneratedDefinitionSetRuleAdapter<T extends DefinitionSet> implements DefinitionSetRuleAdapter<T> {

    protected abstract Collection<Rule> getRules();

    @Override
    public Collection<Rule> getRules(final T pojo) {
        return getRules();
    }

    @Override
    public int getPriority() {
        return 1;
    }

}
