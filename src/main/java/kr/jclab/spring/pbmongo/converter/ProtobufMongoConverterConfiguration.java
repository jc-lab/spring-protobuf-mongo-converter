package kr.jclab.spring.pbmongo.converter;

import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;
import kr.jclab.spring.pbmongo.converter.typeconverters.BinaryToByteStringConverter;
import kr.jclab.spring.pbmongo.converter.typeconverters.ByteStringToBinaryConverter;
import kr.jclab.spring.pbmongo.converter.typeconverters.FromEnumValueDescriptorConverter;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Order(-10)
public class ProtobufMongoConverterConfiguration {
    private Logger logger = LoggerFactory.getLogger(ProtobufMongoConverterConfiguration.class);

    private final PbMongoCustomConversions customConversions = new PbMongoCustomConversions();

    public ProtobufMongoConverterConfiguration() {
        logger.info("created");
    }

    @Autowired
    public void initMappingMongoConverter(MappingMongoConverter mongoConverter) {
        logger.info("initMappingMongoConverter");
        mongoConverter.setCustomConversions(customConversions);
        ConverterRegistry converterRegistry = (ConverterRegistry)mongoConverter.getConversionService();
        converterRegistry.addConverter(ByteString.class, Binary.class, new ByteStringToBinaryConverter());
        converterRegistry.addConverter(Binary.class, ByteString.class, new BinaryToByteStringConverter());
        converterRegistry.addConverter(Descriptors.EnumValueDescriptor.class, String.class, new FromEnumValueDescriptorConverter());
        converterRegistry.addConverter(GeneratedMessageV3.class, Map.class, new GeneratedMessageV3ToMapConverter(mongoConverter));
        converterRegistry.addConverter(new DocumentToGeneratedMessageV3Converter());
    }

    public static class PrimitiveConvertHelper {
        public static List<GenericConverter.ConvertiblePair> WRITE_CONVERTIBLE_PAIRS =
                Collections.unmodifiableList(Arrays.asList(
                        new GenericConverter.ConvertiblePair(ByteString.class, Binary.class),
                        new GenericConverter.ConvertiblePair(Descriptors.EnumValueDescriptor.class, String.class)
                ));
    }
}
