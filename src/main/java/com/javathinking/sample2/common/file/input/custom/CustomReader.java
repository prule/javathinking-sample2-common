package com.javathinking.sample2.common.file.input.custom;

import com.google.common.io.LineReader;
import com.javathinking.sample2.common.file.input.*;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 7/03/2014
 */
public class CustomReader extends AbstractFileParser {
    private Line[] lines;

    public CustomReader(Line[] lines, List<LineParsingListener> listeners) {
        super(listeners);
        this.lines = lines;
    }

    public ObjectReader getObjectReader(Reader reader, final FileFormatContext context) {
        final LineReader lineReader = new LineReader(reader);
        return new ObjectReader() {
            boolean finished = false;

            @Override
            public Object next() throws LineParsingException {
                try {
                    final String textLine = lineReader.readLine();
                    if (textLine == null) {
                        finished = true;
                        return null;
                    }
                    return parseLine(textLine);
                } catch (LineParsingException lpe) {
                    context.error(lpe.getMessage(), lpe);
                    return null;
                } catch (IOException e) {
                    throw new LineParsingException(e);
                }
            }

            @Override
            public boolean finished() {
                return finished;
            }

        };
    }

    private Object parseLine(String textLine) throws LineParsingException {
        for (Line line : lines) {
            if (line.matches(textLine)) {
                return line.toObject(textLine);
            }
        }
        throw new LineParsingException("No match for line");
    }
}
