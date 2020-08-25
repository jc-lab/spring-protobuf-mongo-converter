package kr.jclab.spring.pbmongo.converter;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoConverter;

import java.util.HashMap;
import java.util.Map;

public class GeneratedMessageV3ToMapConverter implements Converter<GeneratedMessageV3, Map<String, Object>>  {
    private final MongoConverter mongoConverter;

    public GeneratedMessageV3ToMapConverter(MongoConverter mongoConverter) {
        this.mongoConverter = mongoConverter;
    }

    @Override
    public Map<String, Object> convert(GeneratedMessageV3 source) {
        HashMap<String, Object> map = new HashMap<>();
        for(Map.Entry<Descriptors.FieldDescriptor, Object> entry : source.getAllFields().entrySet()) {
            map.put(entry.getKey().getName(), mongoConverter.convertToMongoType(entry.getValue()));
        }
        return map;
    }
}
