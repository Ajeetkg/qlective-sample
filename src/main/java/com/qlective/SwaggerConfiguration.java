/**
 * Copyright Illumina, Inc. 2016
 */
package com.qlective;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Pratik Soares
 * @since 1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration
{
	@Bean
	public Docket createApiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.ignoredParameterTypes(Object.class)

				.genericModelSubstitutes(ResponseEntity.class)
				.genericModelSubstitutes(DeferredResult.class)

				.directModelSubstitute(LocalDateTime.class, String.class)
				.directModelSubstitute(LocalDate.class, String.class)

				.useDefaultResponseMessages(false)

				.apiInfo(createApiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.example"))
				.paths(PathSelectors.regex("/api/.*"))

				.build();
	}

	private ApiInfo createApiInfo() {
		return new ApiInfoBuilder()
			.title("DEMO API")
			.version("v1")
			.license("(C) Illumina Inc, All Rights reserved.")
			.build();
	}
}
