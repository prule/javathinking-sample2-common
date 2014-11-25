package com.javathinking.sample2.common.file.input;

/**
 * Date: 7/03/2014
 */
public class FileFormatError {
    private int lineNumber;
    private String message;
    private Exception exception;

    public FileFormatError(int lineNumber, String message, Exception exception) {
        this.lineNumber = lineNumber;
        this.message = message;
        this.exception = exception;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getMessage() {
        return message;
    }

    public Exception getException() {
        return exception;
    }

    @Override
    public String toString() {
        return "FileFormatError{" +
                "lineNumber=" + lineNumber +
                ", message='" + message + '\'' +
                ", exception=" + exception +
                '}';
    }
}
