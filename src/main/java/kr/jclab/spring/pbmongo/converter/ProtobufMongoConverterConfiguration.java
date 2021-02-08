package kr.jclab.spring.pbmongo.converter;

import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageV3;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

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
        converterRegistry.addConverter(GeneratedMessageV3.class, Map.class, new GeneratedMessageV3ToMapConverter(mongoConverter));
        converterRegistry.addConverter(new DocumentToGeneratedMessageV3Converter());
    }
}
