package com.ddalkkak.splitting.swagger.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
/**
 *
 * reqeust body로 multipart-form 데이터를 받고 있는데
 * swagger에서 테스트시에는 각 json 항목별로 타입지정을 할 수 없기 때문에
 *
 * */
//@Component
//public class MultipartJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {
//
//    public MultipartJackson2HttpMessageConverter(ObjectMapper objectMapper) {
//        super(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
//    }
//
//    @Override
//    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
//        return false;
//    }
//
//    @Override
//    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
//        return false;
//    }
//
//    @Override
//    public boolean canWrite(MediaType mediaType) {
//        return false;
//    }
//}
