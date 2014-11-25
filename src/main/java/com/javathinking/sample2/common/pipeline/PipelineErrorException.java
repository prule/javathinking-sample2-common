package com.javathinking.sample2.common.pipeline;

/**
 * Date: 21/03/2014
 */
public class PipelineErrorException extends RuntimeException {
    public PipelineErrorException() {
    }

    public PipelineErrorException(String message) {
        super(message);
    }

    public PipelineErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public PipelineErrorException(Throwable cause) {
        super(cause);
    }

    public PipelineErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
