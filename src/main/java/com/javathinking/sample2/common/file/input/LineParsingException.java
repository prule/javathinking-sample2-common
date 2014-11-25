package com.javathinking.sample2.common.file.input;

public class LineParsingException extends Exception {

    public LineParsingException() {
    }

    public LineParsingException(String message) {
        super(message);
    }

    public LineParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public LineParsingException(Throwable cause) {
        super(cause);
    }

    public LineParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
