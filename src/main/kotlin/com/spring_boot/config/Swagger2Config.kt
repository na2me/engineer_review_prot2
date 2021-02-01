package com.spring_boot.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class Swagger2Config {
    /**
     * @return [Docket] which defines API`s meta info
     */
    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
            .groupName("EngineerReview")
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.regex("/api.*"))
            .build()
            .apiInfo(apiInfo())
            .protocols(setOf("http", "https"))

    /**
     * @return [ApiInfoBuilder] which contains description info about API
     */
    fun apiInfo(): ApiInfo = ApiInfoBuilder()
            .title("EngineerReview Book API")
            .description("API operating Book information")
            .version("1.0")
            .build()
}