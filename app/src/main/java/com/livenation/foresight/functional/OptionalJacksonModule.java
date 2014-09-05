package com.livenation.foresight.functional;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Module for the Optional type.
 * <p>
 *
 * @author Gili Tzabari – https://github.com/FasterXML/jackson-databind/issues/494
 */
public final class OptionalJacksonModule extends SimpleModule {
    private static final long serialVersionUID = 1L;

    private static class OptionalSerializer extends StdSerializer<Optional<?>> {
        /**
         * Create a new OptionalSerializer.
         */
        OptionalSerializer() {
            super(Optional.class, true);
        }

        @Override
        public void serialize(Optional<?> value, JsonGenerator generator, SerializerProvider provider) throws IOException {
            if (value.isPresent())
                generator.writeObject(value.get());
            else
                generator.writeNull();
        }
    }

    private static class OptionalDeserializer extends StdDeserializer<Optional<?>>
            implements ContextualDeserializer {
        private static final long serialVersionUID = 1L;
        private JsonDeserializer<?> deserializer;

        /**
         * Creates a new OptionalDeserializer.
         */
        OptionalDeserializer() {
            super(Optional.class);
        }

        OptionalDeserializer(Class<?> targetClass, JsonDeserializer<?> deserializer) {
            this();

            this.deserializer = deserializer;
        }

        @Override
        public JsonDeserializer<?> createContextual(DeserializationContext context, BeanProperty property) throws JsonMappingException {
            if (property != null) {
                // See http://jackson-users.ning.com/forum/topics/deserialize-with-generic-type
                JavaType type = property.getType();
                JavaType ofType = type.containedType(0);
                Class<?> targetClass = ofType.getRawClass();
                return new OptionalDeserializer(targetClass, context.findContextualValueDeserializer(ofType, property));
            }
            return this;
        }

        @Override
        public Optional<?> deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            return Optional.of(deserializer.deserialize(parser, context));
        }

        @Override
        public Optional<?> getNullValue() {
            return Optional.empty();
        }
    }

    /**
     * Creates a new Jdk18Module.
     */
    public OptionalJacksonModule() {
        super("OptionalJacksonModule", new Version(1, 0, 0, null, "com.fasterxml.jackson.databind", "foresight"));
        addSerializer(new OptionalSerializer());
        addDeserializer(Optional.class, new OptionalDeserializer());
    }
}
