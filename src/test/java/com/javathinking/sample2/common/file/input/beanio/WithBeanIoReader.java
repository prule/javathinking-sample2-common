package com.javathinking.sample2.common.file.input.beanio;

import com.javathinking.sample2.common.file.input.AbstractLineParsingListener;
import com.javathinking.sample2.common.file.input.FileFormatContext;
import com.javathinking.sample2.common.file.input.LineParsingListener;
import com.javathinking.sample2.common.file.input.SysoutLineParsingListener;
import com.javathinking.sample2.common.file.input.beanio.example.DeptTrailer;
import com.javathinking.sample2.common.file.input.beanio.example.Employee;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Date: 8/03/2014
 */
public class WithBeanIoReader {
    private static final Logger log = LoggerFactory.getLogger(WithBeanIoReader.class);
    private BeanIoFileReader reader;



    @Test
    public void aValidFileShouldLoad() throws IOException {
        String file1 = "Header,01012011\n" +
                "DeptHeader,Development\n" +
                "Detail,Joe,Smith,Developer,75000,10012009\n" +
                "Detail,Jane,Doe,Architect,80000,01152008\n" +
                "DeptTrailer,2\n" +
                "DeptHeader,Product Management\n" +
                "Detail,Jon,Anderson,Manager,85000,03182007\n" +
                "DeptTrailer,1\n" +
                "Trailer,2";

        FileFormatContext context = new FileFormatContext();

        reader.parse(new StringReader(file1), context);
        assertThat(context.hasErrors(), is(false));
    }

    @Test
    public void anIncorrectFieldShouldFail() throws IOException {
        String file1 = "Header,01012011\n" +
                "DeptHeader,Development\n" +
                "Detail,Joe,Smith,Developer,A75000,B10012009\n" +
                "Detail,Jane,Doe,Architect,80000,01152008\n" +
                "DeptTrailer,2\n" +
                "DeptHeader,Product Management\n" +
                "Detail,Jon,Anderson,Manager,85000,03182007\n" +
                "DeptTrailer,1\n" +
                "Trailer,2";

        FileFormatContext context = new FileFormatContext();

       reader.parse(new StringReader(file1),context);

        assertThat(context.hasErrors(), is(true));
        assertThat(context.getErrors().size(), is(2));

        assertThat(context.getErrors().get(0).getLineNumber(), Is.is(3));
        assertThat(context.getErrors().get(0).getMessage(), Is.is("Invalid 'employee' record at line 3"));

        assertThat(context.getErrors().get(1).getLineNumber(), Is.is(5));
        assertThat(context.getErrors().get(1).getMessage(), Is.is("Group footer count of 1 does not match expected 2"));

    }


    @Before
    public void setup() throws IOException {
        LineParsingListener groupFooterCheck = new AbstractLineParsingListener() {
            private int count = 0;

            @Override
            public void handleObject(Object object, FileFormatContext context) {
                if (object instanceof Employee) {
                    count++;
                }
                if (object instanceof DeptTrailer) {
                    DeptTrailer groupFooter = (DeptTrailer) object;
                    if (groupFooter.getEmployeeCount().intValue() != count) {
                        context.error("Group footer count of " + count + " does not match expected " + groupFooter.getEmployeeCount());
                    } else {
                        log.debug("Group footer count passed");
                    }
                    count = 0;
                }
            }
        };


        reader = new BeanIoFileReader("employeeFile", this.getClass().getResourceAsStream("/employeeFile.xml"));
        reader.setListeners(Arrays.asList(new SysoutLineParsingListener(), groupFooterCheck));
    }
}
