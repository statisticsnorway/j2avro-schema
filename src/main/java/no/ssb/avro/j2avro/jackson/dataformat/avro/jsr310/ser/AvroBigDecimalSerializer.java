package no.ssb.avro.j2avro.jackson.dataformat.avro.jsr310.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.function.Function;

public class AvroBigDecimalSerializer<T extends BigDecimal> extends StdScalarSerializer<T> {

    private static final long serialVersionUID = 1L;

    public static final AvroBigDecimalSerializer<BigDecimal> INSTANCE =
            new AvroBigDecimalSerializer<>(BigDecimal.class, Function.identity());

    protected AvroBigDecimalSerializer(Class<T> t, Function<T, BigDecimal> getBigDecimal) {
        super(t);
    }

    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeNumber(value.longValue());
    }

}
