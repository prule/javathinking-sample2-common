package com.javathinking.sample2.common.file.input.custom;

import com.javathinking.sample2.common.file.input.LineParsingException;
import com.javathinking.sample2.common.file.input.custom.model.TX;
import com.javathinking.sample2.common.file.input.custom.model.TXObjectFactory;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class WithLine {
    @org.junit.Test
    public void lineShouldProduceMapCorrectly() throws LineParsingException {
        Line line = new LineBuilder()
                .withDefaultDateFormat("yyyyMMdd")
                .startsWith("10")
                .withField().setWidth(10).setFieldName(TXObjectFactory.ACCOUNT).setType(LineField.Type.Text).add()
                .withField().setWidth(8).setFieldName(TXObjectFactory.DATE).setType(LineField.Type.Day).add()
                .withField().setWidth(4).setFieldName(TXObjectFactory.AMOUNT).setType(LineField.Type.Decimal).add()
                .build();

        Map map = line.toMap("10123456789020140101-0.4");
        assertThat((String) map.get(TXObjectFactory.ACCOUNT), equalTo("1234567890"));
        assertThat((DateTime) map.get(TXObjectFactory.DATE), equalTo(new DateTime(2014, 01, 01, 0, 0)));
        assertThat((BigDecimal) map.get(TXObjectFactory.AMOUNT), equalTo(new BigDecimal("-0.4")));
    }

    @org.junit.Test
    public void lineShouldProduceClassCorrectly() throws LineParsingException {
        Line<TX> line = new LineBuilder<TX>()
                .startsWith("10")
                .withObjectFactory(new TXObjectFactory())
                .withField().setWidth(10).setFieldName(TXObjectFactory.ACCOUNT).setDescription("account").setType(LineField.Type.Text).add()
                .withField().setWidth(8).setFieldName(TXObjectFactory.DATE).setDescription("date").setType(LineField.Type.Day).setPattern("yyyyMMdd").add()
                .withField().setWidth(4).setFieldName(TXObjectFactory.AMOUNT).setDescription("amount").setType(LineField.Type.Decimal).add()
                .build();

        TX transaction = line.toObject("10123456789020140101-0.4");
        assertThat(transaction.getAccount(), equalTo("1234567890"));
        assertThat(transaction.getDate(), equalTo(new DateTime(2014, 01, 01, 0, 0)));
        assertThat(transaction.getAmount(), equalTo(new BigDecimal("-0.4")));
    }

    @org.junit.Test
    public void objectShouldFormatToLine() throws LineParsingException {
        Line<TX> line = new LineBuilder<TX>()
                .startsWith("10")
                .withObjectFactory(new TXObjectFactory())
                .withField().setWidth(10).setFieldName(TXObjectFactory.ACCOUNT).setDescription("account").setType(LineField.Type.Text).add()
                .withField().setWidth(8).setFieldName(TXObjectFactory.DATE).setDescription("date").setType(LineField.Type.Day).setPattern("yyyyMMdd").add()
                .withField().setWidth(4).setFieldName(TXObjectFactory.AMOUNT).setDescription("amount").setType(LineField.Type.Decimal).add()
                .build();

        TX transaction = new TX("abc", new DateTime(2014, 1, 1, 0, 3), new BigDecimal("234"));

        assertThat(line.toLine(transaction), equalTo("10abc       20140101234 "));
    }
}
