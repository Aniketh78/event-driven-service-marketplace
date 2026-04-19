package com.booking_micro.APIGateway.config;

import com.booking_micro.APIGateway.Utility.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import io.jsonwebtoken.Claims;


@Component
public class JwtAuthFilter implements GlobalFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        System.out.println("path: " + path);

        if(path.contains("/auth/")) {
            return chain.filter(exchange);
        }
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            exchange.getResponse().getHeaders().add("Content-Type", "application/json");
            String error = "{\"error\": \"Missing or invalid Authorization header.\"}";
            byte[] bytes = error.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            org.springframework.core.io.buffer.DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(reactor.core.publisher.Mono.just(buffer));
        }

        String token = authHeader.substring(7);
        try{
            Claims claims = jwtUtil.validateToken(token);

            String user = claims.get("email", String.class);
            String role = claims.get("role", String.class);

            if (path.contains("/bookings/") && !"USER".equalsIgnoreCase(role)) {
                exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.FORBIDDEN);
                exchange.getResponse().getHeaders().add("Content-Type", "application/json");
                String error = "{\"error\": \"You are not allowed to access this path.\"}";
                byte[] bytes = error.getBytes(java.nio.charset.StandardCharsets.UTF_8);
                org.springframework.core.io.buffer.DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                return exchange.getResponse().writeWith(reactor.core.publisher.Mono.just(buffer));
            }

            ServerHttpRequest request = exchange.getRequest()
                    .mutate()
                    .header("X-User-Email", user)
                    .header("X-User-Role", role)
                    .header("X-User-Id", claims.getSubject())
                    .build();
            return chain.filter(exchange.mutate().request(request).build());
        }
        catch(Exception e){
            exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            exchange.getResponse().getHeaders().add("Content-Type", "application/json");
            String error = "{\"error\": \"Invalid token.\"}";
            byte[] bytes = error.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            org.springframework.core.io.buffer.DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(reactor.core.publisher.Mono.just(buffer));
        }
    }
}
