package com.javathinking.sample2.common.file.input.custom;

import com.javathinking.sample2.common.file.input.LineParsingListener;

import java.util.ArrayList;

/**
 * Date: 7/03/2014
 */
public class FileFormatBuilder {
    private Line[] lines;
    private ArrayList<LineParsingListener> listeners = new ArrayList<>();

    public CustomReader build() {
        return new CustomReader(lines, listeners);
    }

    public FileFormatBuilder withLines(Line... lines) {
        this.lines = lines;
        return this;
    }

    public FileFormatBuilder withListener(LineParsingListener listener) {
        listeners.add(listener);
        return this;
    }
}
