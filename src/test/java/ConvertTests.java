import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageV3;
import kr.jclab.spring.pbmongo.converter.ProtobufMongoConverterConfiguration;
import kr.jclab.spring.pbmongo.testproto.TestProto;
import org.junit.Test;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.NoOpDbRefResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

public class ConvertTests {
    ProtobufMongoConverterConfiguration converterConfiguration = new ProtobufMongoConverterConfiguration();
    MappingMongoConverter mongoConverter = new MappingMongoConverter(NoOpDbRefResolver.INSTANCE, new MongoMappingContext());

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
                .setCake(TestProto.Cake.Crepe)
                .putDataMap(1, "HELLO")
                .putDataMap(2, "WORLD")
                .build();
    }

    public static TestProto.SingleMapMessage createSingleMapMessage() {
        return TestProto.SingleMapMessage.newBuilder()
                .putDataMap(1, "HELLO")
                .putDataMap(2, "WORLD")
                .build();
    }

    public ConvertTests() {
        converterConfiguration.initMappingMongoConverter(mongoConverter);
    }

    @Test
    public void manyTypesTest() {
        TestProto.ManyTypesMessage inputMessage = createMessage();
        Object convertedDocument = mongoConverter.convertToMongoType(inputMessage);
        System.out.println(convertedDocument);

        TestProto.ManyTypesMessage outputMessage = mongoConverter.getConversionService().convert(convertedDocument, TestProto.ManyTypesMessage.class);
        System.out.println(outputMessage);
    }

    @Test
    public void mapInMessageConvertTest() {
        TestProto.SingleMapMessage inputMessage = createSingleMapMessage();
        Object convertedDocument = mongoConverter.convertToMongoType(inputMessage);
        System.out.println(convertedDocument);

        TestProto.SingleMapMessage outputMessage = mongoConverter.getConversionService().convert(convertedDocument, TestProto.SingleMapMessage.class);
        System.out.println(outputMessage);

        assert outputMessage.getDataMapMap().get(2).equals("WORLD");
    }
}
