package kr.jclab.spring.pbmongo.converter;

import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageV3;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PbMongoCustomConversions extends MongoCustomConversions {
    public PbMongoCustomConversions() {
        super(Collections.emptyList());
    }

    public PbMongoCustomConversions(List<?> converters) {
        super(converters);
    }



    @Override
    public Optional<Class<?>> getCustomWriteTarget(Class<?> sourceType) {
        if (GeneratedMessageV3.class.isAssignableFrom(sourceType)) {
            return Optional.of(Map.class);
        }
        if (ByteString.class.isAssignableFrom(sourceType)) {
            return Optional.of(Binary.class);
        }
        return super.getCustomWriteTarget(sourceType);
    }

    @Override
    public Optional<Class<?>> getCustomWriteTarget(Class<?> sourceType, Class<?> requestedTargetType) {
        return super.getCustomWriteTarget(sourceType, requestedTargetType);
    }

    @Override
    public boolean hasCustomReadTarget(Class<?> sourceType, Class<?> targetType) {
        if (GeneratedMessageV3.class.isAssignableFrom(targetType)) {
            return true;
        }
        if (ByteString.class.isAssignableFrom(targetType)) {
            return true;
        }
        return super.hasCustomReadTarget(sourceType, targetType);
    }
}
