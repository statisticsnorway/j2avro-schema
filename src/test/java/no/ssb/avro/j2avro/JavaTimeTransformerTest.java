package no.ssb.avro.j2avro;

import lombok.Data;
import org.apache.avro.Schema;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.time.LocalDate;

class JavaTimeTransformerTest {

    @Data
    static class PojoThatUsesJavaTime {
        private String aString;
        private LocalDate localDate;
    }

    @Test
    void testTransformJavaTime() throws JSONException {
        PojoTransformer transformer = new PojoTransformer();
        Schema avroSchema = transformer.toAvroSchema(PojoThatUsesJavaTime.class);
        //System.out.println(avroSchema.toString(false));
        JSONAssert.assertEquals("{\"type\":\"record\",\"name\":\"PojoThatUsesJavaTime\",\"namespace\":\"no.ssb.avro.j2avro.JavaTimeTransformerTest$\",\"fields\":[{\"name\":\"astring\",\"type\":[\"null\",\"string\"]},{\"name\":\"localDate\",\"type\":[\"null\",{\"type\":\"int\",\"logicalType\":\"date\"}]}]}\n", avroSchema.toString(), true);
    }

}
