package no.ssb.avro.j2avro;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import org.apache.avro.Schema;
import org.apache.avro.SchemaParseException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class PojoTransformerTest {

    @Data
    static class SomePojoRoot {

        private List<SomePojoRoot.SomePojo> aListOfPojos;
        private List<String> aListOfStrings;
        private List<Integer> aListOfIntegers;
        private Set<SomePojoRoot.SomePojo> aSetOfIntegers;
        private Map<String, String> aStringToStringMap;

        enum SomeEnum {
            YES, NO
        }

        @Data
        static class SomePojo {
            private String aString;
            @JsonProperty(defaultValue = "null")
            private String aStringWithDefaultValueNull;
            private int aRequiredInt;
            private Integer anInteger;
            private float aRequiredFloat;
            private Float aFloat;
            private long aRequiredLong;
            private Long aLong;
            private boolean aRequiredBool;
            private Boolean aBoolean;
            private Date aDate;
            private SomeEnum anEnum;
            private String blah;

            @javax.annotation.Nullable
            @JsonProperty(defaultValue = "null")
            @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
            public String getBlah() {
                return blah;
            }

            @JsonProperty(defaultValue = "null")
            @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
            public void setBlah(String blah) {
                this.blah = blah;
            }
        }
    }

    @Test
    void testToAvroSchema() throws JSONException {
        PojoTransformer transformer = new PojoTransformer();
        Schema avroSchema = transformer.toAvroSchema(SomePojoRoot.class.getName());
        System.out.println(avroSchema.toString(true));
        String expected = """
            {
              "type": "record",
              "name": "SomePojoRoot",
              "namespace": "no.ssb.avro.j2avro.PojoTransformerTest$",
              "fields": [
                {
                  "name": "alistOfIntegers",
                  "type": [
                    "null",
                    {
                      "type": "array",
                      "items": {
                        "type": "int",
                        "java-class": "java.lang.Integer"
                      }
                    }
                  ]
                },
                {
                  "name": "alistOfPojos",
                  "type": [
                    "null",
                    {
                      "type": "array",
                      "items": {
                        "type": "record",
                        "name": "SomePojo",
                        "namespace": "no.ssb.avro.j2avro.PojoTransformerTest$SomePojoRoot$",
                        "fields": [
                          {
                            "name": "aStringWithDefaultValueNull",
                            "type": [
                              "null",
                              "string"
                            ],
                            "default": null
                          },
                          {
                            "name": "aboolean",
                            "type": [
                              "null",
                              "boolean"
                            ]
                          },
                          {
                            "name": "adate",
                            "type": [
                              "null",
                              {
                                "type": "long",
                                "java-class": "java.util.Date"
                              }
                            ]
                          },
                          {
                            "name": "afloat",
                            "type": [
                              "null",
                              {
                                "type": "float",
                                "java-class": "java.lang.Float"
                              }
                            ]
                          },
                          {
                            "name": "along",
                            "type": [
                              "null",
                              {
                                "type": "long",
                                "java-class": "java.lang.Long"
                              }
                            ]
                          },
                          {
                            "name": "anEnum",
                            "type": [
                              "null",
                              {
                                "type": "enum",
                                "name": "SomeEnum",
                                "symbols": [
                                  "YES",
                                  "NO"
                                ]
                              }
                            ]
                          },
                          {
                            "name": "anInteger",
                            "type": [
                              "null",
                              {
                                "type": "int",
                                "java-class": "java.lang.Integer"
                              }
                            ]
                          },
                          {
                            "name": "arequiredBool",
                            "type": "boolean"
                          },
                          {
                            "name": "arequiredFloat",
                            "type": {
                              "type": "float",
                              "java-class": "java.lang.Float"
                            }
                          },
                          {
                            "name": "arequiredInt",
                            "type": {
                              "type": "int",
                              "java-class": "java.lang.Integer"
                            }
                          },
                          {
                            "name": "arequiredLong",
                            "type": {
                              "type": "long",
                              "java-class": "java.lang.Long"
                            }
                          },
                          {
                            "name": "astring",
                            "type": [
                              "null",
                              "string"
                            ]
                          },
                          {
                            "name": "astringWithDefaultValueNull",
                            "type": [
                              "null",
                              "string"
                            ],
                            "default": null
                          },
                          {
                            "name": "blah",
                            "type": [
                              "null",
                              "string"
                            ],
                            "default": null
                          }
                        ]
                      }
                    }
                  ]
                },
                {
                  "name": "alistOfStrings",
                  "type": [
                    "null",
                    {
                      "type": "array",
                      "items": "string"
                    }
                  ]
                },
                {
                  "name": "asetOfIntegers",
                  "type": [
                    "null",
                    {
                      "type": "array",
                      "items": "no.ssb.avro.j2avro.PojoTransformerTest$SomePojoRoot$.SomePojo",
                      "java-class": "java.util.Set"
                    }
                  ]
                },
                {
                  "name": "astringToStringMap",
                  "type": [
                    "null",
                    {
                      "type": "map",
                      "values": "string"
                    }
                  ]
                }
              ]
            }    
                """;
        JSONAssert.assertEquals(expected, avroSchema.toString(), true);
    }

    enum EnumWithNumericValues {
        _1("1"),
        _2("2"),
        _5("5");

        private String value;

        EnumWithNumericValues(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static EnumWithNumericValues fromValue(String value) {
            for (EnumWithNumericValues b : EnumWithNumericValues.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }

    @Test
    void enumWithNumericValuesToAvroSchema() {
        PojoTransformer transformer = new PojoTransformer();
        assertThatExceptionOfType(SchemaParseException.class)
                .isThrownBy(
                        () -> transformer.toAvroSchema(EnumWithNumericValues.class)
                )
                .withMessage("Illegal initial character: 1");
    }

}
