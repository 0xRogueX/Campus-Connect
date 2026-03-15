package com.demo.apigateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> apiAuthServiceRoute(){
        // Option 2: Route /api/auth/** to auth service (handling your current request path)
        return GatewayRouterFunctions.route("api_auth_service")
                .route(RequestPredicates.path("/api/auth/**"),
                        HandlerFunctions.http("http://localhost:8080/api/auth/"))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> apiCourseServiceRoute(){
        // Option 2: Route /api/auth/** to auth service (handling your current request path)
        return GatewayRouterFunctions.route("api_course_service")
                .route(RequestPredicates.path("/api/branches/**"),
                        HandlerFunctions.http("http://localhost:8070/api/branches/"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> apiEventServiceRoute(){
        // Option 2: Route /api/auth/** to auth service (handling your current request path)
        return GatewayRouterFunctions.route("api_event_service")
                .route(RequestPredicates.path("/api/events/**"),
                        HandlerFunctions.http("http://localhost:8060/api/events/"))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> apiExamsServiceRoute(){
        // Option 2: Route /api/auth/** to auth service (handling your current request path)
        return GatewayRouterFunctions.route("api_course_service")
                .route(RequestPredicates.path("/api/exams/**"),
                        HandlerFunctions.http("http://localhost:8050/api/exams/"))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> apiMaterialServiceRoute(){
        // Option 2: Route /api/auth/** to auth service (handling your current request path)
        return GatewayRouterFunctions.route("api_materials_service")
                .route(RequestPredicates.path("/api/materials/**"),
                        HandlerFunctions.http("http://localhost:8081/api/materials/"))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> apiProfilesServiceRoute(){
        // Option 2: Route /api/auth/** to auth service (handling your current request path)
        return GatewayRouterFunctions.route("api_profiles_service")
                .route(RequestPredicates.path("/api/profiles/**"),
                        HandlerFunctions.http("http://localhost:8030/api/profiles/"))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> apiResultServiceRoute(){
        // Option 2: Route /api/auth/** to auth service (handling your current request path)
        return GatewayRouterFunctions.route("api_result_service")
                .route(RequestPredicates.path("/api/results/**"),
                        HandlerFunctions.http("http://localhost:8020/api/results/"))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> apiScheduleServiceRoute(){
        // Option 2: Route /api/auth/** to auth service (handling your current request path)
        return GatewayRouterFunctions.route("api_schedule_service")
                .route(RequestPredicates.path("/api/schedule/**"),
                        HandlerFunctions.http("http://localhost:8010/api/schedule/"))
                .build();
    }

}