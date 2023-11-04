package br.com.libdolf.urlshortener.utils;

import br.com.libdolf.urlshortener.repository.UrlRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@AllArgsConstructor
@Transactional
public class DeletingExpiredData {

    private UrlRepository repository;

    @Scheduled(fixedRate = 120000)
    public void removeExpiredData(){
        Date dataActual = new Date();
        Date dataExpiration = new Date(dataActual.getTime() - 120000);

        repository.deleteByExpirationDateBefore(dataExpiration);

    }
}
