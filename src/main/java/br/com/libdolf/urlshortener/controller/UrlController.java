package br.com.libdolf.urlshortener.controller;

import br.com.libdolf.urlshortener.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/")
@AllArgsConstructor
public class UrlController {

    private UrlService service;

    @GetMapping
    public ResponseEntity<String> getNewShortenedUrl(@RequestParam("url") String url){
        return ResponseEntity.ok(service.getNewShortenedUrl(url));
    }

    @GetMapping("{urlCode}")
    public ResponseEntity<String> redirectShortenedUrl(@PathVariable String urlCode, HttpServletResponse response) throws Exception {
        response.sendRedirect(service.findShortenUrl(urlCode));
       return ResponseEntity.ok().build();
    }
}
