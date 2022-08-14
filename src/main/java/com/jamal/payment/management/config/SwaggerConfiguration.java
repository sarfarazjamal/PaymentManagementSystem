package com.jamal.payment.management.config;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	/**
	 * This is used for swagger API .apis(RequestHandlerSelectors.any()) for all API
	 *
	 * @return Docket
	 */
	private final Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);

	@Bean
	public Docket apiPublic() {
		String groupName = "payment-management_public";
		String swagger_host="localhost:8088";
		log.debug("<---swaggerHost Public----->" + swagger_host);
		return new Docket(DocumentationType.SWAGGER_2).host(swagger_host).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/api/**")).build().groupName(groupName)
				.securitySchemes(Lists.newArrayList(apiKey())).securityContexts(Lists.newArrayList(securityContext()))
				.apiInfo(apiInfo());

	}

	/*
	 * @Bean public Docket apiAdmin() { String groupName = "Biogenix_admin"; String
	 * swagger_host="localhost:8088"; log.debug("<---swaggerHost admin----->" +
	 * swaggerHost); return new
	 * Docket(DocumentationType.SWAGGER_2).host(swagger_host).select().apis(
	 * RequestHandlerSelectors.any())
	 * .paths(PathSelectors.ant("/api/admin/v1/**")).build().groupName(groupName)
	 * .securitySchemes(Lists.newArrayList(apiKey())).securityContexts(Lists.
	 * newArrayList(securityContext())) .apiInfo(apiInfo()); }
	 */
	private Predicate<String> petstorePaths() {
		return or(regex("/api/account/*"), regex("/api/users/"));
	}

	/**
	 * This is used for securityContext
	 *
	 * @return
	 */
	@Bean
	SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
	}

	/**
	 * This is used for default Authentication
	 *
	 * @return
	 */
	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
	}

	/**
	 * This is used for API key
	 *
	 * @return SecurityScheme
	 */
	@Bean
	SecurityScheme apiKey() {
		return new ApiKey("JWT", "Authorization", "header");
	}

	/**
	 * This is used for API Info
	 *
	 * @return ApiInfo
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Power Plant API Docs").description("Power Plant API Description").version("2.0")
				.build();
	}
}
