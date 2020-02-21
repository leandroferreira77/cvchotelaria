package br.com.cvc.corp.hotelaria.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket productAPI() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.cvc.corp.hotelaria"))
				.paths(PathSelectors.regex("/api.*"))
				.build()
				.apiInfo(metaInfo());

	}

	private ApiInfo metaInfo() {
		return new ApiInfoBuilder()
				.title("Hotelaria API REST")
				.description("API REST de Consulta de Hotéis.")
				.version("1.0.0")
				.contact(new Contact("Leandro Ferreira", null, "leandro.ferreira777@gmail.com"))
				.build();
	}
}
