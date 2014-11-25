package com.javathinking.sample2.common.file.input.custom;

import com.javathinking.sample2.common.file.input.AbstractLineParsingListener;
import com.javathinking.sample2.common.file.input.FileFormatContext;
import com.javathinking.sample2.common.file.input.LineParsingListener;
import com.javathinking.sample2.common.file.input.custom.model.GF;
import com.javathinking.sample2.common.file.input.custom.model.TX;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 9/03/2014
 */
public class FileFixture {
    private static final Logger log = LoggerFactory.getLogger(FileFixture.class);

    public static final String fileWithInvalidBigDecimalAmounts =
            "HH20140101\n" +
                    "GHCLIENT1   \n" +
                    "TXacc1      201401010001\n" +
                    "TYacc2      20140101000A\n" +
                    "GF0002\n" +
                    "GHCLIENT2   \n" +
                    "TXacc1b     201401010001\n" +
                    "TYacc2b     20140101000C\n" +
                    "TYacc2c     201401010002\n" +
                    "GF0003\n" +
                    "FF00000002";

    public static final String fileWithInvalidGroupTxnAmounts =
            "HH20140101\n" +
                    "GHCLIENT1   \n" +
                    "TXacc1      201401010001\n" +
                    "TYacc2      201401010002\n" +
                    "GF0099\n" +
                    "GHCLIENT2   \n" +
                    "TXacc1b     201401010001\n" +
                    "TYacc2b     201401010002\n" +
                    "TYacc2c     201401010002\n" +
                    "GF0003\n" +
                    "FF00000002";

    public static final String validFile =
            "HH20140101\n" +
                    "GHCLIENT1   \n" +
                    "TXacc1      201401010001\n" +
                    "TYacc2      201401010002\n" +
                    "GF0002\n" +
                    "GHCLIENT2   \n" +
                    "TXacc1b     201401010001\n" +
                    "TYacc2b     201401010002\n" +
                    "TYacc2c     201401010002\n" +
                    "GF0003\n" +
                    "FF00000002";

    public static final LineParsingListener groupFooterCheck = new AbstractLineParsingListener() {
        private int count = 0;

        @Override
        public void handleObject(Object object, FileFormatContext context) {
            if (object instanceof TX) {
                count++;
            }
            if (object instanceof GF) {
                GF groupFooter = (GF) object;
                if (groupFooter.getCount().intValue() != count) {
                    context.error("Group footer count of " + count + " does not match expected " + groupFooter.getCount());
                } else {
                    log.debug("Group footer count passed");
                }
                count = 0;
            }
        }
    };

}
