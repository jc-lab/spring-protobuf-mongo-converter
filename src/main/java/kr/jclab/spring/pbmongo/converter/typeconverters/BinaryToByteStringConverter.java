package kr.jclab.spring.pbmongo.converter.typeconverters;

import com.google.protobuf.ByteString;
import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;

public class BinaryToByteStringConverter implements Converter<Binary, ByteString>  {
    public BinaryToByteStringConverter() {
    }

    @Override
    public ByteString convert(Binary source) {
        return ByteString.copyFrom(source.getData());
    }
}
