package com.javathinking.sample2.common.file.input.custom;

import com.google.common.base.Strings;
import com.javathinking.sample2.common.file.input.LineParsingException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.math.BigDecimal;


public class LineField {

    // TODO break this down into a FieldType class which knows how to translate its own type then we can remove the switch statements
    public enum Type {
        Text, Day, Decimal
    }

    public enum Align {Left, Right}

    private int width;
    private String description;
    private Type type;
    private String pattern;
    private String fieldName;
    private char paddedWith = ' ';
    private Align alignment = Align.Left;

    public LineField(int width, String fieldName, Type type, String pattern, String description) {
        this.width = width;
        this.description = description;
        this.type = type;
        this.pattern = pattern;
        this.fieldName = fieldName;
    }

    public LineField(int width, String fieldName, Type type, String pattern, String description, char paddedWith, Align alignment) {
        this(width, fieldName, type, pattern, description);
        this.paddedWith = paddedWith;
        this.alignment = alignment;
    }

    public int getWidth() {
        return width;
    }

    public String getDescription() {
        return description;
    }

    public Type getType() {
        return type;
    }

    public String getPattern() {
        return pattern;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String formatAsString(Object o) {
        String value;
        switch (type) {
            case Text:
            case Decimal:
                value = o.toString();
                break;
            case Day:
                value = DateTimeFormat.forPattern(pattern).print((DateTime) o);
                break;
            default:
                throw new IllegalArgumentException("Invalid type for field " + fieldName);
        }
        switch (alignment) {
            case Left:
                return Strings.padEnd(value, width, paddedWith);
            case Right:
                return Strings.padStart(value, width, paddedWith);
            default:
                throw new IllegalArgumentException("Invalid alignment for field " + fieldName);
        }
    }

    /**
     * Throws an IllegalArgumentException in the event of a problem parsing data
     */
    public Object extract(int currentPosition, String line) throws LineParsingException {
        int endPosition = currentPosition + width;
        String fragment;
        try {
            fragment = line.substring(currentPosition, endPosition);
            switch (type) {
                case Text:
                    return fragment;
                case Day:
                    return DateTimeFormat.forPattern(pattern).parseDateTime(fragment);
                case Decimal:
                    return new BigDecimal(fragment.trim());
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw new LineParsingException("Unexpected end of line - Expect line length>=" + endPosition + " but was " + line.length());
        } catch (Exception e) {
            throw new LineParsingException("Could not parse '" + line + "' from " + currentPosition + " to " + endPosition + " as " + type, e);
        }
        throw new LineParsingException("Unsupported field type [" + fragment + "]");
    }
}
