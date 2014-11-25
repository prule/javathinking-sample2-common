package com.javathinking.sample2.common.file.input.custom;

import com.javathinking.sample2.common.file.input.LineParsingException;
import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class WithField {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void aDateShouldParse() throws LineParsingException {
        LineField field = new LineField(8, "date", LineField.Type.Day, "yyyyMMdd", null);
        Object value = field.extract(0, "20140101");
        assertThat((DateTime) value, equalTo(new DateTime(2014, 01, 01, 0, 0)));
    }

    @Test
    public void aZeroPaddedDecimalShouldParse() throws LineParsingException {
        LineField field = new LineField(10, "decimal", LineField.Type.Decimal, null, null);
        Object value = field.extract(0, "-00123.456");
        assertThat((BigDecimal) value, equalTo(new BigDecimal("-123.456")));
    }

    @Test
    public void aSpacePaddedDecimalShouldParse() throws LineParsingException {
        LineField field = new LineField(10, "decimal", LineField.Type.Decimal, null, null);
        Object value = field.extract(0, "  -123.456");
        assertThat((BigDecimal) value, equalTo(new BigDecimal("-123.456")));
    }

    @Test
    public void anInvalidLineLengthShouldThrowException() throws LineParsingException {
        thrown.expect(LineParsingException.class);
        new LineField(10, "decimal", LineField.Type.Decimal, null, null)
                .extract(0, "-123.456");
    }

    @Test
    public void anInvalidDecimalThrowException() throws LineParsingException {
        thrown.expect(LineParsingException.class);
        thrown.expectMessage("Could not parse");
        thrown.expectCause(is(NumberFormatException.class));
        new LineField(8, "decimal", LineField.Type.Decimal, null, null)
                .extract(0, "-12X.456");
    }

    @Test
    public void aDecimalFieldShouldBePadded() throws LineParsingException {
        LineField field = new LineField(10, "decimal", LineField.Type.Decimal, null, null, '0', LineField.Align.Right);
        final String value = field.formatAsString(new BigDecimal(1234));
        assertThat(value, equalTo("0000001234"));
    }


    @Test
    public void aTextFieldShouldBePadded() throws LineParsingException {
        LineField field = new LineField(6, "decimal", LineField.Type.Text, null, null, '0', LineField.Align.Right);
        final String value = field.formatAsString("1234");
        assertThat(value, equalTo("001234"));
    }


    @Test
    public void aDateFieldShouldBePadded() throws LineParsingException {
        LineField field = new LineField(12, "decimal", LineField.Type.Day, "yyyyMMdd", null, '0', LineField.Align.Right);

        final String value = field.formatAsString(new DateTime(2014, 1, 1, 0, 3));
        assertThat(value, equalTo("000020140101"));
    }

}
