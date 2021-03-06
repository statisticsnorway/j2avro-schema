package no.ssb.avro.j2avro;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.schema.AvroSchemaGenerator;
import com.fasterxml.jackson.datatype.threetenbp.ThreeTenModule;
import no.ssb.avro.j2avro.jackson.dataformat.avro.jsr310.AvroJavaTimeStringModule;
import org.apache.avro.Schema;

class PojoTransformer {

    /**
     * Generates an avro schema based on java Pojo. This could e.g. be a reference to the root class generated by
     * openapi-generator (OpenApi -> java)
     *
     * @param rootClassName fully qualified class name of the root that represents the avro schema to generate
     * @return an avro schema
     * @throws Exception
     */
    public Schema toAvroSchema(String rootClassName) {
        AvroMapper mapper = AvroMapper.builder()
                .addModule(new AvroJavaTimeStringModule())
                .addModule(new ThreeTenModule())
                .build();

        AvroSchemaGenerator gen = new AvroSchemaGenerator();
        gen.enableLogicalTypes();

        final Class<?> rootPojoClass;
        try {
            rootPojoClass = Class.forName(rootClassName);
        }
        catch (ClassNotFoundException e) {
            throw new AvroSchemaGeneratorException("Make sure your classpath is correctly configured.", e);
        }

        try {
            mapper.acceptJsonFormatVisitor(rootPojoClass, gen);
        } catch (JsonMappingException e) {
            throw new AvroSchemaGeneratorException("Error generating avro schema from " + rootClassName, e);
        }

        return gen.getGeneratedSchema().getAvroSchema();
    }

    public Schema toAvroSchema(Class<?> clazz) {
        return toAvroSchema(clazz.getName());
    }

}
