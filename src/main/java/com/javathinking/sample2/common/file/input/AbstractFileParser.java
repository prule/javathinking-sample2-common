package com.javathinking.sample2.common.file.input;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Date: 7/03/2014
 */
public abstract class AbstractFileParser implements FileParser {
    private static final Logger log = LoggerFactory.getLogger(AbstractFileParser.class);

    private List<LineParsingListener> listeners = new ArrayList<>();

    public AbstractFileParser(List<LineParsingListener> listeners) {
        this.listeners = listeners;
    }

    public void setListeners(List<LineParsingListener> listeners) {
        this.listeners = listeners;
    }

    public FileFormatContext parse(Reader reader, FileFormatContext context) throws IOException {
        final ObjectReader objectReader = getObjectReader(reader, context);
        Stopwatch stopwatch = Stopwatch.createStarted();

        beforeListeners(context);
        try {
            do {
                try {
                    Object object = objectReader.next();
                    if (object != null) {
                        invokeListeners(object, context);
                        context.addState(object);
                    }
                } catch (LineParsingException e) {
                    context.error("Problem parsing line " + e.getMessage(), e);
                }
                context.inc();
                if (context.getLineNumber() % 1000 == 0) {
                    log.debug("Processed line " + context.getLineNumber() + " @ " + stopwatch.elapsed(TimeUnit.SECONDS) + " seconds");
                }
            } while (!objectReader.finished());
        } finally {
            afterListeners(context);
        }

        stopwatch.stop();
        log.debug("Took " + stopwatch.toString());

        return context;
    }

    private void beforeListeners(FileFormatContext context) {
        for (LineParsingListener listener : listeners) {
            listener.before(context);
        }
    }
    private void afterListeners(FileFormatContext context) {
        for (LineParsingListener listener : listeners) {
            listener.after(context);
        }
    }

    protected void invokeListeners(Object object, FileFormatContext context) {
        for (LineParsingListener listener : listeners) {
            if (listener.supports(object)) {
                listener.handleObject(object, context);
            }
        }
    }

}
