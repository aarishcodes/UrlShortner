package com.urlshortner.UrlShortner.respository;

import com.urlshortner.UrlShortner.entity.UrlMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<UrlMapping, String> {
}
