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

package org.gradle.internal.execution.impl.steps;

import org.gradle.internal.execution.ExecutionResult;
import org.gradle.internal.execution.UnitOfWork;

public class PrepareCachingStep<C extends Context> implements Step<C> {
    private final Step<? super CachingContext> delegate;

    public PrepareCachingStep(Step<? super CachingContext> delegate) {
        this.delegate = delegate;
    }

    @Override
    public ExecutionResult execute(C context) {
        CacheHandler cacheHandler = context.getWork().createCacheHandler();
        return delegate.execute(new CachingContext() {
            @Override
            public CacheHandler getCacheHandler() {
                return cacheHandler;
            }

            @Override
            public UnitOfWork getWork() {
                return context.getWork();
            }
        });
    }
}
