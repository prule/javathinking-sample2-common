package com.javathinking.sample2.common.file.input.custom;

public class LineFieldBuilder {
    private int width;
    private String description;
    private LineField.Type type;
    private String pattern;
    private LineBuilder parentBuilder;
    private String fieldName;

    public LineFieldBuilder(LineBuilder lineBuilder) {
        this.parentBuilder = lineBuilder;
    }

    public LineFieldBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public LineFieldBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public LineFieldBuilder setType(LineField.Type type) {
        this.type = type;
        return this;
    }

    public LineFieldBuilder setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public LineField build() {
        return new LineField(width, fieldName, type, pattern, description);
    }

    public LineBuilder add() {
        parentBuilder.addField(this.build());
        return parentBuilder;
    }

    public LineFieldBuilder setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }
}