package kr.jclab.spring.pbmongo.converter.typeconverters;

import com.google.protobuf.Descriptors;
import org.springframework.core.convert.converter.Converter;

public class FromEnumValueDescriptorConverter implements Converter<Descriptors.EnumValueDescriptor, String>  {
    public FromEnumValueDescriptorConverter() {
    }

    @Override
    public String convert(Descriptors.EnumValueDescriptor source) {
        return source.getName();
    }
}
