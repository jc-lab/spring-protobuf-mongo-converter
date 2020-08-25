package kr.jclab.spring.pbmongo.converter.annotation;

import kr.jclab.spring.pbmongo.converter.ProtobufMongoConverterConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@Import(ProtobufMongoConverterConfiguration.class)
public @interface EnableProtobufMongoConverter {
}
