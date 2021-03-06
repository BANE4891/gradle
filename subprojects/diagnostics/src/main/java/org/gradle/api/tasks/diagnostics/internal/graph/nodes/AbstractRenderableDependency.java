/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.api.tasks.diagnostics.internal.graph.nodes;

import org.gradle.api.artifacts.result.ResolvedVariantResult;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class AbstractRenderableDependency implements RenderableDependency {
    @Override
    public Object getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public ResolvedVariantResult getResolvedVariant() {
        return null;
    }

    @Override
    public ResolutionState getResolutionState() {
        return ResolutionState.UNRESOLVED;
    }

    @Override
    public Set<? extends RenderableDependency> getChildren() {
        return Collections.emptySet();
    }

    @Override
    public List<Section> getExtraDetails() {
        return Collections.emptyList();
    }
}
