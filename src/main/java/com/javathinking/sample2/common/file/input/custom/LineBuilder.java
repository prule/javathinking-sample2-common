package com.javathinking.sample2.common.file.input.custom;

import java.util.ArrayList;
import java.util.List;

public class LineBuilder<T> {
    private String startsWith;
    private LineFieldBuilder lineFieldBuilder;
    private List<LineField> fields = new ArrayList();
    private ObjectFactory objectFactory;
    private String defaultDateFormat;


    public LineFieldBuilder withField() {
        lineFieldBuilder = new LineFieldBuilder(this);
        lineFieldBuilder.setPattern(defaultDateFormat);
        return lineFieldBuilder;
    }

    public LineBuilder addField(LineField lineField) {
        fields.add(lineField);
        return this;
    }

    public LineBuilder startsWith(String id) {
        this.startsWith = id;
        return this;
    }

    public Line<T> build() {
        return new Line(startsWith, fields, objectFactory);
    }

    public LineBuilder withObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
        return this;
    }

    public LineBuilder withDefaultDateFormat(String dateFormat) {
        this.defaultDateFormat = dateFormat;
        return this;
    }
}
