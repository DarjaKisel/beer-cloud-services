package com.dzinevich.gateway.config

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.PredicateSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("local-discovery")
@Configuration
class LoadBalancedRoutes {
    @Bean
    fun loadBalancedRoutesConfig(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
                .route { r: PredicateSpec ->
                    r.path("/v2/beer*", "/v2/beer/*", "/v2/beerUpc/*")
                            .uri("lb://brewery")
                }
                .route { r: PredicateSpec ->
                    r.path("/api/v1/beer/*/inventory")
                            .uri("lb://beer-inventory")
                }
                .route { r: PredicateSpec ->
                    r.path("/api/v1/customers/**")
                            .uri("lb://beer-order")
                }
                .build()
    }
}