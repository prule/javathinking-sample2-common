package com.javathinking.sample2.common.file.input.custom;

import com.javathinking.sample2.common.file.input.FileFormatContext;
import com.javathinking.sample2.common.file.input.LineParsingException;
import com.javathinking.sample2.common.file.input.SysoutLineParsingListener;
import com.javathinking.sample2.common.file.input.custom.model.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class WithCustomReader {
    CustomReader customReader;

    @Before
    public void setup() throws LineParsingException, IOException {

        // "HH20140101"
        Line headerline = new LineBuilder()
                .withDefaultDateFormat("yyyyMMdd")
                .startsWith("HH")
                .withField().setWidth(8).setFieldName(HHObjectFactory.DATE).setType(LineField.Type.Day).add()
                .withObjectFactory(new HHObjectFactory())
                .build();

        // "GHCLIENT1   "
        Line groupHeaderline = new LineBuilder()
                .startsWith("GH")
                .withField().setWidth(10).setFieldName(GHObjectFactory.CLIENT).setType(LineField.Type.Text).add()
                .withObjectFactory(new GHObjectFactory())

                .build();

        // TXacc1      201401010001
        Line transactionlineA = new LineBuilder()
                .withDefaultDateFormat("yyyyMMdd")
                .startsWith("TX")
                .withField().setWidth(10).setFieldName(TXObjectFactory.ACCOUNT).setType(LineField.Type.Text).add()
                .withField().setWidth(8).setFieldName(TXObjectFactory.DATE).setType(LineField.Type.Day).add()
                .withField().setWidth(4).setFieldName(TXObjectFactory.AMOUNT).setType(LineField.Type.Decimal).add()
                .withObjectFactory(new TXObjectFactory())
                .build();

        // TYacc2      201401010002
        Line transactionlineB = new LineBuilder()
                .withDefaultDateFormat("yyyyMMdd")
                .startsWith("TY")
                .withField().setWidth(10).setFieldName(TXObjectFactory.ACCOUNT).setType(LineField.Type.Text).add()
                .withField().setWidth(8).setFieldName(TXObjectFactory.DATE).setType(LineField.Type.Day).add()
                .withField().setWidth(4).setFieldName(TXObjectFactory.AMOUNT).setType(LineField.Type.Decimal).add()
                .withObjectFactory(new TXObjectFactory())
                .build();

        // "GF0002"
        Line groupFooterline = new LineBuilder()
                .startsWith("GF")
                .withField().setWidth(4).setFieldName(GFObjectFactory.COUNT).setType(LineField.Type.Decimal).add()
                .withObjectFactory(new GFObjectFactory())
                .build();

        // "FF00000001"
        Line footerline = new LineBuilder()
                .withDefaultDateFormat("yyyyMMdd")
                .startsWith("FF")
                .withField().setWidth(8).setFieldName(FFObjectFactory.COUNT).setType(LineField.Type.Decimal).add()
                .withObjectFactory(new FFObjectFactory())
                .build();


        customReader = new FileFormatBuilder()
                .withLines(headerline, groupHeaderline, transactionlineA, transactionlineB, groupFooterline, footerline)
                .withListener(new SysoutLineParsingListener())
                .withListener(FileFixture.groupFooterCheck)
                .build();

    }

    @Test
    public void aValidFileShouldParse() throws IOException {
        FileFormatContext context = new FileFormatContext();

                customReader.parse(new StringReader(
                FileFixture.validFile
        ), context);

        assertThat(context.getErrors(), is(empty()));
    }

    @Test
    public void aFileWithIncorrectGroupTxnCountShouldReportError() throws IOException {
        FileFormatContext context = new FileFormatContext();
        customReader.parse(new StringReader(
                FileFixture.fileWithInvalidGroupTxnAmounts
        ),context);

        assertThat(context.getErrors(), is(hasSize(1)));
    }

    @Test
    public void anInvalidFieldShouldReportError() throws IOException {
        FileFormatContext context = new FileFormatContext();
        customReader.parse(new StringReader(
                FileFixture.fileWithInvalidBigDecimalAmounts
        ), context);

        assertThat(context.getErrors(), is(hasSize(4)));

        assertThat(context.getErrors().get(0).getLineNumber(), is(4));
        assertThat(context.getErrors().get(0).getMessage(), is("Could not parse 'TYacc2      20140101000A' from 20 to 24 as Decimal"));

        assertThat(context.getErrors().get(1).getLineNumber(), is(5));
        assertThat(context.getErrors().get(1).getMessage(), is("Group footer count of 1 does not match expected 2"));

        assertThat(context.getErrors().get(2).getLineNumber(), is(8));
        assertThat(context.getErrors().get(2).getMessage(), is("Could not parse 'TYacc2b     20140101000C' from 20 to 24 as Decimal"));

        assertThat(context.getErrors().get(3).getLineNumber(), is(10));
        assertThat(context.getErrors().get(3).getMessage(), is("Group footer count of 2 does not match expected 3"));
    }

}
