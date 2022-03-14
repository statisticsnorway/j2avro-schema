package no.ssb.avro.j2avro;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class SomePojoRoot {

    private List<SomePojo> aListOfPojos;
    private List<String> aListOfStrings;
    private List<Integer> aListOfIntegers;
    private Set<SomePojo> aSetOfIntegers;
    private Map<String, String> aStringToStringMap;

    @Data
    public static class SomePojo {
        private String aString;
        private int aRequiredInt;
        private Integer anInteger;
        private float aRequiredFloat;
        private Float aFloat;
        private long aRequiredLong;
        private Long aLong;
        private boolean aRequiredBool;
        private Boolean aBoolean;
        private Date aDate;
    }

}
