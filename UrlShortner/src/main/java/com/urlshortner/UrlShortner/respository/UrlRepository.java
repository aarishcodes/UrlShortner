package com.urlshortner.UrlShortner.respository;

import com.urlshortner.UrlShortner.entity.UrlMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UrlRepository extends MongoRepository<UrlMapping, String> {
    Optional<UrlMapping> findByShortCode(String shortCode);
    Optional<UrlMapping> findByOriginalUrl(String originalUrl);
    List<UrlMapping> findAllByExpiresAtBefore(LocalDateTime date);
}
