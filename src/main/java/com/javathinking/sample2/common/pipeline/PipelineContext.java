package com.javathinking.sample2.common.pipeline;

import com.javathinking.commons.validation.Errors;

/**
 * Date: 12/03/2014
 */
public abstract class PipelineContext {


    private Result result;
    private Exception exception;
    private Errors validationErrors;

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public Errors getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Errors validationErrors) {
        this.validationErrors = validationErrors;
    }

    enum Result {Fail, Success}

    ;
}
