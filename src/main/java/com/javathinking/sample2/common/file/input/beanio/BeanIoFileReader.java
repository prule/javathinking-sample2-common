package com.javathinking.sample2.common.file.input.beanio;


import com.javathinking.sample2.common.file.input.*;
import org.beanio.BeanReader;
import org.beanio.BeanReaderErrorHandler;
import org.beanio.BeanReaderException;
import org.beanio.StreamFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;

/**
 * Date: 8/03/2014
 */
public class BeanIoFileReader extends AbstractFileParser {
    private String streamName;
    private StreamFactory factory;

    public BeanIoFileReader(String streamName, InputStream streamSchema, LineParsingListener... listeners) throws IOException {
        super(Arrays.asList(listeners));
        this.streamName = streamName;
        factory = StreamFactory.newInstance();
        factory.load(streamSchema);
    }


    public ObjectReader getObjectReader(Reader reader, final FileFormatContext context) {
        final BeanReader in = factory.createReader(streamName, reader);
        in.setErrorHandler(new BeanReaderErrorHandler() {
            @Override
            public void handleError(BeanReaderException ex) throws Exception {
                context.error(ex.getMessage(), ex);
                context.inc();
            }
        });

        return new ObjectReader() {
            boolean finished = false;

            @Override
            public Object next() throws LineParsingException {
                Object obj = in.read();
                if (obj == null) {
                    finished = true;
                    in.close(); // ???
                }
                return obj;
            }

            @Override
            public boolean finished() {
                return finished;
            }

        };
    }


}
