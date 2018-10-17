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

package org.gradle.internal.execution;

import javax.annotation.Nullable;

public abstract class WorkResult {
    public abstract WorkOutcome getOutcome();
    @Nullable
    public abstract Throwable getFailure();

    public static WorkResult success(WorkOutcome outcome) {
        return outcome.asResult();
    }

    public static WorkResult failure(Throwable failure) {
        return new WorkResult() {
            @Override
            public WorkOutcome getOutcome() {
                return WorkOutcome.FAILED;
            }

            @Override
            public Throwable getFailure() {
                return failure;
            }
        };
    }
}
