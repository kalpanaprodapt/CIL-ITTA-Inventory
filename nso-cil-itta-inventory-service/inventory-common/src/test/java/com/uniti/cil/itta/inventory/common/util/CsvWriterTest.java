package com.uniti.cil.itta.inventory.common.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringWriter;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class CsvWriterTest {

    @Test
    void escapesCommasAndQuotes() {
        StringWriter out = new StringWriter();
        new CsvWriter(out).writeRow(Arrays.asList("a,b", "say \"hi\"", null, 5));
        assertThat(out.toString()).isEqualTo("\"a,b\",\"say \"\"hi\"\"\",,5\n");
    }
}
