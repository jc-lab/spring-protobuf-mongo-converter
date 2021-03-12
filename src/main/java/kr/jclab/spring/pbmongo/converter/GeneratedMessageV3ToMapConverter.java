package kr.jclab.spring.pbmongo.converter;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.MapEntry;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratedMessageV3ToMapConverter implements Converter<GeneratedMessageV3, Map<String, Object>>  {
    private final MongoConverter mongoConverter;

    public GeneratedMessageV3ToMapConverter(MongoConverter mongoConverter) {
        this.mongoConverter = mongoConverter;
    }

    private Map convertMap(List list) {
        Map map = new HashMap();
        for (Object item : list) {
            MapEntry entry = (MapEntry)item;
            map.put(entry.getKey(), mongoConverter.convertToMongoType(entry.getValue()));
        }
        return map;
    }

    @Override
    public Map<String, Object> convert(GeneratedMessageV3 source) {
        HashMap<String, Object> map = new HashMap<>();
        for (Descriptors.FieldDescriptor entry : source.getDescriptorForType().getFields()) {
            Object value = source.getField(entry);
            if (entry.isMapField()) {
                map.put(entry.getName(), convertMap((List)value));
            } else {
                map.put(entry.getName(), mongoConverter.convertToMongoType(value));
            }
        }
        return map;
    }
}
