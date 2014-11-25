package com.javathinking.sample2.common.pipeline;

import com.javathinking.commons.validation.Validator;

import java.util.Map;

/**
 * Date: 23/03/2014
 */
public class ValidationTask<T extends PipelineContext> implements PipelineTask<T> {
    private Validator validator;
    private boolean stopIfErrors = false;

    public ValidationTask(Validator validator, boolean stopIfErrors) {
        this.validator = validator;
        this.stopIfErrors = stopIfErrors;
    }

    @Override
    public boolean execute(T context, Map data) throws Exception {
        context.setValidationErrors(validator.validate(context));
        if(stopIfErrors) {
            return context.getValidationErrors().isValid();
        } else {
            return true;
        }
    }

    @Override
    public String getName() {
        return "Validation";
    }
}
