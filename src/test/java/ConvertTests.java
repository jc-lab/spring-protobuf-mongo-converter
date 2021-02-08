import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageV3;
import kr.jclab.spring.pbmongo.converter.GeneratedMessageV3ToMapConverter;
import kr.jclab.spring.pbmongo.converter.ProtobufMongoConverterConfiguration;
import kr.jclab.spring.pbmongo.testproto.TestProto;
import org.junit.Test;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.convert.NoOpDbRefResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.Map;

public class ConvertTests {
    public static TestProto.ManyTypesMessage createMessage() {
        return TestProto.ManyTypesMessage.newBuilder()
                .setDataBool(true)
                .setDataInt32(1234)
                .setDataInt64(12345678987654321L)
                .setDataFloat(3.14159f)
                .setDataString("Hello World")
                .setDataBytes(ByteString.copyFrom(new byte[] { 1, 2, 3, 4, 5}))
                .setDataMessage(
                        TestProto.SmallMessage.newBuilder()
                                .setData("Test1234")
                                .build()
                )
                .addDataRepeatedString("AAAA")
                .addDataRepeatedString("BBBB")
                .addDataRepeatedMessage(
                        TestProto.SmallMessage.newBuilder()
                                .setData("CCCC")
                                .build()
                )
                .addDataRepeatedMessage(
                        TestProto.SmallMessage.newBuilder()
                                .setData("DDDD")
                                .build()
                )
                .build();
    }

    @Test
    public void manyTypesTest() {
        ProtobufMongoConverterConfiguration converterConfiguration = new ProtobufMongoConverterConfiguration();
        MappingMongoConverter mongoConverter = new MappingMongoConverter(NoOpDbRefResolver.INSTANCE, new MongoMappingContext());
        converterConfiguration.initMappingMongoConverter(mongoConverter);

        TestProto.ManyTypesMessage inputMessage = createMessage();
        Object convertedDocument = mongoConverter.convertToMongoType(inputMessage);
        System.out.println(convertedDocument);

        TestProto.ManyTypesMessage outputMessage = mongoConverter.getConversionService().convert(convertedDocument, TestProto.ManyTypesMessage.class);
        System.out.println(outputMessage);
    }
}
