package com.uitmerch.backend.common.config;

import com.uitmerch.backend.common.model.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.Instant;
import java.util.Map;

@Hidden
@RestController
public class HealthController {

    @GetMapping("/")
    public ResponseEntity<?> health(HttpServletRequest request) {
        String accept = request.getHeader(HttpHeaders.ACCEPT);
        if (accept != null && accept.contains("text/html")) {
            return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:5174"))
                .build();
        }
        Map<String, Object> data = Map.of(
            "status", "UP",
            "app", "Concera Backend",
            "timestamp", Instant.now().toString(),
            "links", Map.of(
                "swagger", "/swagger-ui.html",
                "openapi", "/v3/api-docs",
                "frontend", "http://localhost:5174"
            )
        );
        return ResponseEntity.ok(ApiResponse.success("Concera Backend is running", data));
    }
}
