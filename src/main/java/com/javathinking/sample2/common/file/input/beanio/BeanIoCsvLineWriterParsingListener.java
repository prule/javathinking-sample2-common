package com.javathinking.sample2.common.file.input.beanio;

import com.javathinking.sample2.common.file.input.AbstractLineParsingListener;
import com.javathinking.sample2.common.file.input.FileFormatContext;
import com.javathinking.sample2.common.file.input.FileFormatConverter;
import org.beanio.BeanWriter;
import org.beanio.StreamFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Writes a given class to a CSV file
 */
public class BeanIoCsvLineWriterParsingListener extends AbstractLineParsingListener {
    private BeanWriter beanWriter;
    private File outputFile;
    private StreamFactory factory;
    private Writer out;
    private String beanIoStream;
    private FileFormatConverter converter;

    public BeanIoCsvLineWriterParsingListener(File outputFile, Class type, String beanIoResource, String beanIoStream, FileFormatConverter converter) throws IOException {
        super(type);
        this.outputFile = outputFile;
        this.beanIoStream = beanIoStream;
        this.converter = converter;
        this.factory = StreamFactory.newInstance();
        this.factory.loadResource(beanIoResource);
    }

    @Override
    public void handleObject(Object object, FileFormatContext context) {
        beanWriter.write(converter.convert(object, context));
    }

    @Override
    public void after(FileFormatContext context) {
        beanWriter.close();
        try {
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void before(FileFormatContext context) {
        try {
            out = new BufferedWriter(new FileWriter(outputFile));
            beanWriter = factory.createWriter(beanIoStream, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
