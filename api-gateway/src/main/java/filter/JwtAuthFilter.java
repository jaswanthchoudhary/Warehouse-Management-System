package filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${internal.secret}")
    private String internalSecret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        
        if (path.startsWith("/api-auth")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return deny(exchange, HttpStatus.UNAUTHORIZED);
        }

        try {
            String token = authHeader.substring(7);
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String role = claims.get("role", String.class);

            System.out.println(role);
            System.out.println(role);
            System.out.println(role);
            System.out.println(role);
            System.out.println(role);
            System.out.println(role);
            if (path.startsWith("/api/products/admin") ||
                path.startsWith("/api/warehouses/admin") ||
                path.startsWith("/api/employees/admin")) {
                if (!role.equalsIgnoreCase("ADMIN")) {
                    return deny(exchange, HttpStatus.FORBIDDEN);
                }
            }

            if (path.startsWith("/api/products/admanage") ||
                path.startsWith("/api/warehouses/admanage") ||
                path.startsWith("/api/employees/admanage")) {
                if (!(role.equalsIgnoreCase("ADMIN") || role.equalsIgnoreCase("MANAGER"))) {
                    return deny(exchange, HttpStatus.FORBIDDEN);
                }
            }

            if (path.startsWith("/api/products/manager") ||
                path.startsWith("/api/warehouses/manager") ||
                path.startsWith("/api/employees/manager")) {
                if (!role.equalsIgnoreCase("MANAGER")) {
                    return deny(exchange, HttpStatus.FORBIDDEN);
                }
            }

            if (path.startsWith("/api/products/workmanage") ||
                path.startsWith("/api/warehouses/workmanage") ||
                path.startsWith("/api/employees/workmanage")) {
                if (!(role.equalsIgnoreCase("MANAGER") || role.equalsIgnoreCase("WORKER"))) {
                    return deny(exchange, HttpStatus.FORBIDDEN);
                }
            }

            if (path.startsWith("/api/products/worker") ||
                path.startsWith("/api/warehouses/worker") ||
                path.startsWith("/api/employees/worker")) {
                if (!role.equalsIgnoreCase("WORKER")) {
                    return deny(exchange, HttpStatus.FORBIDDEN);
                }
            }

            
            ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(exchange.getRequest().mutate()
                            .header("X-Internal-Access", internalSecret)
                            .build())
                    .build();

            return chain.filter(modifiedExchange);

        } catch (Exception e) {
            return deny(exchange, HttpStatus.UNAUTHORIZED);
        }
    }

    private Mono<Void> deny(ServerWebExchange exchange, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
