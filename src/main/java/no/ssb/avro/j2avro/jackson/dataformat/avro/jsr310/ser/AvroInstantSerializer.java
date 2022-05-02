package no.ssb.avro.j2avro.jackson.dataformat.avro.jsr310.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.function.Function;

/**
 * Serializer for variants of java.time classes (Instant, OffsetDateTime, ZonedDateTime) into long value.
 *
 * Serialized value represents an instant on the global timeline, independent of a particular time zone or
 * calendar, with a precision of one millisecond from the unix epoch, 1 January 1970 00:00:00.000 UTC.
 * Please note that time zone information gets lost in this process. Upon reading a value back, we can only
 * reconstruct the instant, but not the original representation.
 *
 * Note: In combination with {@link com.fasterxml.jackson.dataformat.avro.schema.AvroSchemaGenerator#enableLogicalTypes()}
 * it aims to produce Avro schema with type long and logicalType timestamp-millis:
 * {
 *   "type" : "long",
 *   "logicalType" : "timestamp-millis"
 * }
 *
 * {@link AvroInstantSerializer} does not support serialization to string.
 *
 * @param <T> The type of a instant class that can be serialized.
 */
public class AvroInstantSerializer<T extends Temporal> extends StdScalarSerializer<T> {

    private static final long serialVersionUID = 1L;

    public static final AvroInstantSerializer<Instant> INSTANT =
            new AvroInstantSerializer<>(Instant.class, Function.identity());

    public static final AvroInstantSerializer<OffsetDateTime> OFFSET_DATE_TIME =
            new AvroInstantSerializer<>(OffsetDateTime.class, OffsetDateTime::toInstant);

    public static final AvroInstantSerializer<ZonedDateTime> ZONED_DATE_TIME =
            new AvroInstantSerializer<>(ZonedDateTime.class, ZonedDateTime::toInstant);

    private final Function<T, Instant> getInstant;

    protected AvroInstantSerializer(Class<T> t, Function<T, Instant> getInstant) {
        super(t);
        this.getInstant = getInstant;
    }

    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        /**
         * Number of milliseconds, independent of a particular time zone or calendar,
         * from 1 January 1970 00:00:00.000 UTC.
         */
        final Instant instant = getInstant.apply(value);
        gen.writeString(String.valueOf(instant.toEpochMilli()));
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
        JsonStringFormatVisitor v2 = visitor.expectStringFormat(typeHint);
        if (v2 != null) {
            v2.format(JsonValueFormat.UTC_MILLISEC);
        }
    }

}
