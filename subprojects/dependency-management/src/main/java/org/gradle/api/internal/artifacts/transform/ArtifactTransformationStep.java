/*
 * Copyright 2017 the original author or authors.
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

package org.gradle.api.internal.artifacts.transform;

import org.gradle.api.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class ArtifactTransformationStep implements ArtifactTransformation {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArtifactTransformationStep.class);

    private final Transformer transformer;
    private final TransformerInvoker transformerInvoker;

    public ArtifactTransformationStep(Transformer transformer, TransformerInvoker transformerInvoker) {
        this.transformer = transformer;
        this.transformerInvoker = transformerInvoker;
    }

    @Override
    public TransformationSubject transform(TransformationSubject subjectToTransform) {
        if (subjectToTransform.getFailure() != null) {
            return subjectToTransform;
        }
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Executing transform {} on {}", transformer.getDisplayName(), subjectToTransform.getDisplayName());
            }
        List<File> result = new ArrayList<File>();
        for (File file : subjectToTransform.getFiles()) {
            TransformerInvocation invocation = new TransformerInvocation(transformer, file, subjectToTransform);
            transformerInvoker.invoke(invocation);
            if (invocation.getFailure() != null) {
                return new DefaultTransformationSubject(subjectToTransform, invocation.getFailure());
            }
            result.addAll(invocation.getResult());
        }
        return new DefaultTransformationSubject(subjectToTransform, result);
    }

    @Override
    public boolean hasCachedResult(TransformationSubject subject) {
        if (subject.getFailure() != null) {
            return true;
        }
        for (File file : subject.getFiles()) {
            if (!transformerInvoker.hasCachedResult(file, transformer)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getDisplayName() {
        return transformer.getDisplayName();
    }

    @Override
    public void visitTransformationSteps(Action<? super ArtifactTransformation> action) {
        action.execute(this);
    }

    @Override
    public String toString() {
        return String.format("%s@%s", transformer.getDisplayName(), transformer.getInputsHash());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArtifactTransformationStep that = (ArtifactTransformationStep) o;
        return transformer.equals(that.transformer);
    }

    @Override
    public int hashCode() {
        return transformer.hashCode();
    }
}
