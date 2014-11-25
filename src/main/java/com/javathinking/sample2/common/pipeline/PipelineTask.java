package com.javathinking.sample2.common.pipeline;

import java.util.Map;

public interface PipelineTask<T extends PipelineContext> {
    /**
     * Returns true if the pipeline should continue, or false to abort the pipeline
     */
    boolean execute(T context, Map data) throws Exception;

    String getName();
}
