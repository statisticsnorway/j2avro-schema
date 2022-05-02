package no.ssb.avro.j2avro.jackson.dataformat.avro.jsr310;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.avro.PackageVersion;
import no.ssb.avro.j2avro.jackson.dataformat.avro.jsr310.deser.AvroInstantDeserializer;
import no.ssb.avro.j2avro.jackson.dataformat.avro.jsr310.deser.AvroLocalDateDeserializer;
import no.ssb.avro.j2avro.jackson.dataformat.avro.jsr310.deser.AvroLocalDateTimeDeserializer;
import no.ssb.avro.j2avro.jackson.dataformat.avro.jsr310.deser.AvroLocalTimeDeserializer;
import no.ssb.avro.j2avro.jackson.dataformat.avro.jsr310.ser.*;

import java.math.BigDecimal;
import java.time.*;

/**
 * A module that installs a collection of serializers and deserializers for java.time classes.
 *
 * AvroJavaTimeModule module is to be used either as:
 *   - replacement of Java 8 date/time module (com.fasterxml.jackson.datatype.jsr310.JavaTimeModule) or
 *   - to override Java 8 date/time module and for that, module must be registered AFTER Java 8 date/time module.
 */
public class AvroJavaTimeStringModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public AvroJavaTimeStringModule() {
        super(AvroJavaTimeStringModule.class.getName(), PackageVersion.VERSION);

        addSerializer(Instant.class, AvroInstantSerializer.INSTANT);
        addSerializer(OffsetDateTime.class, AvroInstantSerializer.OFFSET_DATE_TIME);
        addSerializer(ZonedDateTime.class, AvroInstantSerializer.ZONED_DATE_TIME);
        addSerializer(LocalDateTime.class, AvroLocalDateTimeSerializer.INSTANCE);
        addSerializer(LocalDate.class, AvroLocalDateSerializer.INSTANCE);
        addSerializer(LocalTime.class, AvroLocalTimeSerializer.INSTANCE);

        addDeserializer(Instant.class, AvroInstantDeserializer.INSTANT);
        addDeserializer(OffsetDateTime.class, AvroInstantDeserializer.OFFSET_DATE_TIME);
        addDeserializer(ZonedDateTime.class, AvroInstantDeserializer.ZONED_DATE_TIME);
        addDeserializer(LocalDateTime.class, AvroLocalDateTimeDeserializer.INSTANCE);
        addDeserializer(LocalDate.class, AvroLocalDateDeserializer.INSTANCE);
        addDeserializer(LocalTime.class, AvroLocalTimeDeserializer.INSTANCE);

        addSerializer(BigDecimal.class, AvroBigDecimalSerializer.INSTANCE);
    }

}
