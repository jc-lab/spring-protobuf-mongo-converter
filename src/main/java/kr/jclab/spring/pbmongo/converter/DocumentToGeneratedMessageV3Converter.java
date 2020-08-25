package kr.jclab.spring.pbmongo.converter;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import org.bson.Document;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DocumentToGeneratedMessageV3Converter implements ConditionalGenericConverter {
    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return Document.class.isAssignableFrom(sourceType.getType()) || Map.class.isAssignableFrom(sourceType.getType());
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return new HashSet<>(Arrays.asList(
                new ConvertiblePair(Document.class, GeneratedMessageV3.class),
                new ConvertiblePair(Map.class, GeneratedMessageV3.class)
        ));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        Class<? extends GeneratedMessageV3> targetClazz = (Class<? extends GeneratedMessageV3>)targetType.getObjectType();
        try {
            Method getDefaultInstanceMethod = targetClazz.getMethod("getDefaultInstance", new Class[] {});
            GeneratedMessageV3 defaultInstance = (GeneratedMessageV3)getDefaultInstanceMethod.invoke(null);
            Message.Builder builder = defaultInstance.newBuilderForType();
            Map document = (Map)source;
            JsonFormatEx.parser().merge(BsonUtils.toJsonElement(document), builder);
            return builder.build();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
