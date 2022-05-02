package no.ssb.avro.j2avro;

import lombok.Data;
import org.apache.avro.Schema;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.threeten.bp.LocalDate;

class ThreetenTimeTransformerTest {

    @Data
    static class PojoThatUsesThreetenTime {
        private String aString;
        private LocalDate localDate;
    }

    @Test
    void testTransformJavaTime() throws JSONException {
        PojoTransformer transformer = new PojoTransformer();
        Schema avroSchema = transformer.toAvroSchema(PojoThatUsesThreetenTime.class);
        System.out.println(avroSchema.toString(true));
        String expected = """
                {
                  "type" : "record",
                  "name" : "PojoThatUsesThreetenTime",
                  "namespace" : "no.ssb.avro.j2avro.ThreetenTimeTransformerTest$",
                  "fields" : [ {
                    "name" : "astring",
                    "type" : [ "null", "string" ]
                  }, {
                    "name" : "localDate",
                    "type" : [ "null", {
                      "type" : "array",
                      "items" : "int"
                    } ]
                  } ]
                }
                """;
        JSONAssert.assertEquals(expected, avroSchema.toString(), true);
    }

}
