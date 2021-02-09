package kr.jclab.spring.pbmongo.converter;

import com.google.protobuf.GeneratedMessageV3;
import org.springframework.core.convert.converter.GenericConverter;
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
        for (GenericConverter.ConvertiblePair pair : ProtobufMongoConverterConfiguration.PrimitiveConvertHelper.WRITE_CONVERTIBLE_PAIRS) {
            if (pair.getSourceType().isAssignableFrom(sourceType)) {
                return Optional.of(pair.getTargetType());
            }
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
        return super.hasCustomReadTarget(sourceType, targetType);
    }
}
