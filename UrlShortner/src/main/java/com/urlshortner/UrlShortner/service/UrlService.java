package com.urlshortner.UrlShortner.service;

import com.urlshortner.UrlShortner.entity.UrlMapping;
import com.urlshortner.UrlShortner.respository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public UrlMapping shortUrl(String originalUrl) {

        Optional<UrlMapping> existing = urlRepository.findByOriginalUrl(originalUrl);
        if (existing.isPresent()) return existing.get();

        String shortCode = generateShortCode();
        UrlMapping mapping = new UrlMapping();

        mapping.setShortCode(shortCode);
        mapping.setOriginalUrl(originalUrl);
        mapping.setCreatedAt(LocalDateTime.now());
        mapping.setExpiresAt(LocalDateTime.now().plusDays(30));
        mapping.setClickCount(0);
        return urlRepository.save(mapping);
    }

    public String generateShortCode() {
//        return UUID.randomUUID().toString().substring(0, 6);

        String code;
        do {
            code = UUID.randomUUID().toString().substring(0, 6);
        } while (urlRepository.findById(code).isPresent());
        return code;
    }

    public Optional<UrlMapping> getOriginalUrl(String shortCode) {
//        return urlRepository.findById(shortCode);

        Optional<UrlMapping> result = urlRepository.findById(shortCode);
        if (result.isPresent()) {
            UrlMapping mapping = result.get();
            if (mapping.getExpiresAt() != null && mapping.getExpiresAt().isBefore(LocalDateTime.now())) {
                return Optional.empty(); // URL is expired
            }
        }
        return result;
    }

    public void incrementClickCount(UrlMapping mapping) {
        mapping.setClickCount(mapping.getClickCount() + 1);
        urlRepository.save(mapping);
    }
}
