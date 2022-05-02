package no.ssb.avro.j2avro;

import lombok.Data;
import org.apache.avro.Schema;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

class JavaTimeTransformerTest {

    @Data
    static class PojoThatUsesJavaTime {
        private String someString;
        private Instant someInstant;
        private LocalDate someLocalDate;
        private LocalDateTime someLocalDateTime;
        private LocalTime someLocalTime;
    }

    @Test
    void testTransformJavaTime() throws JSONException {
        PojoTransformer transformer = new PojoTransformer();
        Schema avroSchema = transformer.toAvroSchema(PojoThatUsesJavaTime.class);
        System.out.println(avroSchema.toString(true));
        String expected = """
                {
                  "type" : "record",
                  "name" : "PojoThatUsesJavaTime",
                  "namespace" : "no.ssb.avro.j2avro.JavaTimeTransformerTest$",
                  "fields" : [ {
                    "name" : "someInstant",
                    "type" : [ "null", "string" ]
                  }, {
                    "name" : "someLocalDate",
                    "type" : [ "null", "string" ]
                  }, {
                    "name" : "someLocalDateTime",
                    "type" : [ "null", "string" ]
                  }, {
                    "name" : "someLocalTime",
                    "type" : [ "null", "string" ]
                  }, {
                    "name" : "someString",
                    "type" : [ "null", "string" ]
                  } ]
                }                """;
        JSONAssert.assertEquals(expected, avroSchema.toString(), true);
    }

}
