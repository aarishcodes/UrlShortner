package com.urlshortner.UrlShortner.controller;


import com.sun.net.httpserver.HttpsServer;
import com.urlshortner.UrlShortner.entity.UrlMapping;
import com.urlshortner.UrlShortner.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shorten(@RequestBody String url) {
        UrlMapping res = urlService.shortUrl(url);
        return ResponseEntity.ok("Shortened URL: http://localhost:8080/"+ res.getShortCode());
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirect(@PathVariable String shortCode) {
        Optional<UrlMapping> mapping = urlService.getOriginalUrl(shortCode);
        if (mapping.isPresent()) {
            urlService.incrementClickCount(mapping.get());
//            return ResponseEntity.status(HttpStatus.FOUND)
//                    .location(URI.create(mapping.get().getOriginalUrl()))
//                    .build();
           return new ResponseEntity<>(mapping.get().getClickCount(), HttpStatus.FOUND);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Short URL not found");
        }
    }

    @GetMapping("/info/{shortCode}")
    public ResponseEntity<?> getInfo(@PathVariable String shortCode) {
        Optional<UrlMapping> mapping = urlService.getOriginalUrl(shortCode);
        if(mapping.isPresent()) {
            return new ResponseEntity<>(mapping.get().getOriginalUrl(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
    }
}
