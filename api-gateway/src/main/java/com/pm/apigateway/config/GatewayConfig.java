package com.pm.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Auth service route
                .route("auth-service-route", r -> r.path("/auth/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://patient-service:4000"))
                .build();

        /*
        // FOR auth-service route
        .route("auth-service-route", r -> r.path("/auth/**")
    .filters(f -> f.stripPrefix(1))
    .uri("http://auth-service:4005"))
         */
    }
}

/* When we need to build more routes then we can add them for example

                // Patient service route
                .route("patient-service-route", r -> r.path("/api/patients/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://patient-service:4000"))

                // API docs for patient service
                .route("api-docs-patient-route", r -> r.path("/api-docs/patients")
                        .filters(f -> f.rewritePath("/api-docs/patients", "/v3/api-docs"))
                        .uri("http://patient-service:4000"))

                // API docs for auth service
                .route("api-docs-auth-route", r -> r.path("/api-docs/auth")
                        .filters(f -> f.rewritePath("/api-docs/auth", "/v3/api-docs"))
                        .uri("http://auth-service:4005"))
 */
