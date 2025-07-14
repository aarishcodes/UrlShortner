package com.urlshortner.UrlShortner.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "urls")
public class UrlMapping {
    @Id
    private String shortCode;

    @NotNull
    private String originalUrl;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private long clickCount;
}
