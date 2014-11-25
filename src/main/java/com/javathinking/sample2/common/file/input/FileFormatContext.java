package com.javathinking.sample2.common.file.input;

import java.util.*;

/**
 * Date: 7/03/2014
 */
public class FileFormatContext {
    private List<FileFormatError> errors = new ArrayList();
    private Map<Class, Object> state = new HashMap();
    private int lineNumber = 1;
    private String fileRef;

    public void error(String message) {
        errors.add(new FileFormatError(lineNumber, message, null));
    }

    public void error(String message, Exception e) {
        errors.add(new FileFormatError(lineNumber, message, e));
    }

    public void inc() {
        lineNumber++;
    }

    public List<FileFormatError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public void addState(Object object) {
        state.put(object.getClass(), object);
    }

    public Object getState(Class clz) {
        return state.get(clz);
    }

    public void setFileRef(String fileRef) {
        this.fileRef = fileRef;
    }

    public String getFileRef() {
        return fileRef;
    }
}
