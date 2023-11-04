package br.com.libdolf.urlshortener.service;

import br.com.libdolf.urlshortener.entitiy.UrlSchema;
import br.com.libdolf.urlshortener.repository.UrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UrlService {

    private UrlRepository repository;

    public String getNewShortenedUrl(String urlOriginal) {
       if(repository.findByUrlOriginalEquals(urlOriginal).isEmpty()){
           String urlShortened = urlShorten(urlOriginal);
           repository.save(UrlSchema.builder()
                           .urlOriginal(urlOriginal)
                           .urlShortened(urlShortened)
                           .expirationDate(new Date())
                   .build());
           return urlShortened;
       }
       else return repository.findByUrlOriginalEquals(urlOriginal)
               .orElseThrow()
               .getUrlShortened();
    }

    public String findShortenUrl(String urlCode){
        return repository.findByUrlShortenedContaining(urlCode).orElseThrow().getUrlOriginal();
    }

    private String urlShorten(String urlOriginal){
        String urlShortened = buildShortenedUrl(urlOriginal);
        while (!repository.findByUrlShortenedEquals(urlShortened).isEmpty()){
            urlShortened = buildShortenedUrl(urlOriginal);
        }
        return urlShortened;
    }

    private String getServerUrl(String urlOriginal){
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest()
                .getRequestURL()
                .toString().replace(urlOriginal, "");
    }
    private String buildShortenedUrl(String urlOriginal){
        return getServerUrl(urlOriginal) + UUID.randomUUID()
                .toString()
                .substring(0, 10)
                .replace("-","");
    }
}
