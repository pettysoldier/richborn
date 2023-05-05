package com.yami.shop.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * ---------\,,,/---------
 * ---------(o o)---------
 * -----oOOo-(3)-oOOo-----
 *
 * @Description:
 * @Author: zhushengguo
 * @Version:
 * @Date: 4:10 下午 2021/5/12
 **/

@Configuration
public class RestTemplateConfig {
    @Autowired
    private CloseableHttpClient httpClient;
    @Autowired
    private ObjectMapper mapper;
    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient);
        clientHttpRequestFactory.setConnectTimeout(20000);
        return clientHttpRequestFactory;
    }
//    @Bean
//    public ObjectMapper objectMapper(){
//        ObjectMapper objectMapper = new ObjectMapper();
//        //空值不参加序列化
//        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
//        //反序列化的时候如果多了其他属性,不抛出异常
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        //如果是空对象的时候,不抛异常
//        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//        //取消时间的转化格式,默认是时间戳,可以取消,同时需要设置要表现的时间格式
//        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//        //objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
//        return objectMapper;
//    }
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate template =  new RestTemplateBuilder()
                .requestFactory(this::clientHttpRequestFactory)
                .messageConverters(new MappingJackson2XmlHttpMessageConverter(),
                        new StringHttpMessageConverter(),
                        new WxMappingJackson2HttpMessageConverter(),
                        new ResourceHttpMessageConverter(false),
                        new AllEncompassingFormHttpMessageConverter(),
                        new ByteArrayHttpMessageConverter(),
                        new SourceHttpMessageConverter(),
                        new MappingJackson2HttpMessageConverter(mapper)
                ).build();
        return template;
    }

    public class WxMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        public WxMappingJackson2HttpMessageConverter(){
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.TEXT_PLAIN);
            mediaTypes.add(MediaType.TEXT_HTML);
            setSupportedMediaTypes(mediaTypes);
        }
    }

    @Bean
    public CloseableHttpClient httpClient() {
        return HttpClientBuilder.create().build();
    }
}
