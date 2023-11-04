package br.com.libdolf.urlshortener.repository;

import br.com.libdolf.urlshortener.entitiy.UrlSchema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlSchema, Long> {
    Optional<UrlSchema> findByUrlShortenedEquals(String urlShortened);
    Optional<UrlSchema> findByUrlShortenedContaining(String urlCode);
    Optional<UrlSchema> findByUrlOriginalEquals(String urlOriginal);

    void deleteByExpirationDateBefore(Date dataExpiration);
}
