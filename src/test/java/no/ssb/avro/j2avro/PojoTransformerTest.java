package no.ssb.avro.j2avro;

import org.apache.avro.Schema;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

class PojoTransformerTest {

    @Test
    void testToAvroSchema() throws JSONException {
        PojoTransformer transformer = new PojoTransformer();
        Schema avroSchema = transformer.toAvroSchema(SomePojoRoot.class.getName());
        //System.out.println(avroSchema.toString(true));
        JSONAssert.assertEquals("{\"type\":\"record\",\"name\":\"SomePojoRoot\",\"namespace\":\"no.ssb.avro.j2avro\",\"fields\":[{\"name\":\"alistOfIntegers\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"int\",\"java-class\":\"java.lang.Integer\"}}]},{\"name\":\"alistOfPojos\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"SomePojo\",\"namespace\":\"no.ssb.avro.j2avro.SomePojoRoot$\",\"fields\":[{\"name\":\"aboolean\",\"type\":[\"null\",\"boolean\"]},{\"name\":\"adate\",\"type\":[\"null\",{\"type\":\"long\",\"java-class\":\"java.util.Date\"}]},{\"name\":\"afloat\",\"type\":[\"null\",{\"type\":\"float\",\"java-class\":\"java.lang.Float\"}]},{\"name\":\"along\",\"type\":[\"null\",{\"type\":\"long\",\"java-class\":\"java.lang.Long\"}]},{\"name\":\"anInteger\",\"type\":[\"null\",{\"type\":\"int\",\"java-class\":\"java.lang.Integer\"}]},{\"name\":\"arequiredBool\",\"type\":\"boolean\"},{\"name\":\"arequiredFloat\",\"type\":{\"type\":\"float\",\"java-class\":\"java.lang.Float\"}},{\"name\":\"arequiredInt\",\"type\":{\"type\":\"int\",\"java-class\":\"java.lang.Integer\"}},{\"name\":\"arequiredLong\",\"type\":{\"type\":\"long\",\"java-class\":\"java.lang.Long\"}},{\"name\":\"astring\",\"type\":[\"null\",\"string\"]}]}}]},{\"name\":\"alistOfStrings\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"string\"}]},{\"name\":\"asetOfIntegers\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"no.ssb.avro.j2avro.SomePojoRoot$.SomePojo\",\"java-class\":\"java.util.Set\"}]},{\"name\":\"astringToStringMap\",\"type\":[\"null\",{\"type\":\"map\",\"values\":\"string\"}]}]}\n", avroSchema.toString(), true);
    }

}
