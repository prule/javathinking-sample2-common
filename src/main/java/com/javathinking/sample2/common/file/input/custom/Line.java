package com.javathinking.sample2.common.file.input.custom;

import com.javathinking.sample2.common.file.input.LineParsingException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Line<T> {
    private String startsWith;
    private List<LineField> fields;
    private ObjectFactory objectFactory;

    public Line(String startsWith, List<LineField> fields, ObjectFactory objectFactory) {
        this.startsWith = startsWith;
        this.fields = fields;
        this.objectFactory = objectFactory;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public List<LineField> getFields() {
        return Collections.unmodifiableList(fields);
    }

    public Map toMap(String line) throws LineParsingException {
        if (line.startsWith(startsWith)) {
            TreeMap<String, Object> map = new TreeMap<>();
            int currentPosition = startsWith.length();
            for (LineField field : fields) {
                Object value = field.extract(currentPosition, line);
                if (value != null) {
                    map.put(field.getFieldName(), value);
                }
                currentPosition += field.getWidth();
            }
            return map;
        }
        return null;
    }

    public T toObject(String line) throws LineParsingException {
        return (T) objectFactory.fromMap(toMap(line));
    }

    public String toLine(T object) {
        final Map map = objectFactory.toMap(object);
        final StringBuilder sb = new StringBuilder(getStartsWith());
        for (LineField field : fields) {
            sb.append(field.formatAsString(map.get(field.getFieldName())));
        }
        return sb.toString();
    }

    public boolean matches(String textLine) {
        return textLine.startsWith(getStartsWith());
    }
}
