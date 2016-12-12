package com.CD.util;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * ProjectName: QJZ
 * PackageName: com.CD.util
 * User: C0dEr
 * Date: 2016-11-10
 * Time: 10:51
 * Description:
 */
@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("去酒庄Api")
                .description("去酒庄Api管理文档,\n" +
                        "所有返回值结构为{\n" +
                        "  \"message\": \"\",\n" +
                        "  \"statusCode\": 1,\n" +
                        "  \"data\": null\n" +
                        "};" +
                        "message存放返回消息;\n" +
                        "statusCode存放状态，0成功，1失败;\n" +
                        "data存放返回的。\n" +
                        "接口文档返回数据没做说明的，默认说明的是data中的字段含义。")
                .version("V1.0.0")
                .contact(new Contact("zjl", "#", "zhujl@zepan.com.cn"))
                .build();
    }


}
