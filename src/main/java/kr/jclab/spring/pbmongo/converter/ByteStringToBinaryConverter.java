package kr.jclab.spring.pbmongo.converter;

import com.google.protobuf.ByteString;
import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;

public class ByteStringToBinaryConverter implements Converter<ByteString, Binary>  {
    public ByteStringToBinaryConverter() {
    }

    @Override
    public Binary convert(ByteString source) {
        return new Binary(source.toByteArray());
    }
}
