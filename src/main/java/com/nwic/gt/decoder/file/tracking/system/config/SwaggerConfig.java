package com.nwic.gt.decoder.file.tracking.system.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Telemetry Decoder File tracking APIs",
                description = "This is Telemetry Decoder File Tracking .",
                version = "1.0"
        )
)
public class SwaggerConfig {

//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                // Select the base package to scan for REST controllers
//                .apis(RequestHandlerSelectors.basePackage("com.yourcompany.controller")) //https://nwic.gov.in/
//                // Include all paths (you can modify this to filter specific paths)
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(new ApiInfoBuilder()
//                        .title("Telemetry File Tracking")
//                        .description("This is a sample API documentation using Swagger 2")
//                        .version("1.0")
//                        .build());
//    }
}