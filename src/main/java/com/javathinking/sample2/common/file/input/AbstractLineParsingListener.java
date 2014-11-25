package com.javathinking.sample2.common.file.input;

import java.util.Arrays;
import java.util.List;

/**
 * Date: 7/03/2014
 */
public abstract class AbstractLineParsingListener implements LineParsingListener {
    private List<Class> supports;

    public AbstractLineParsingListener() {
    }

    public AbstractLineParsingListener(Class... supports) {
        this.supports = Arrays.asList(supports);
    }

    public boolean supports(Object object) {
        if (supports == null) return true;
        return supports.contains(object.getClass());
    }

    @Override
    public void after(FileFormatContext context) {
        // override if you need to
    }

    @Override
    public void before(FileFormatContext context) {
        // override if you need to
    }
}
