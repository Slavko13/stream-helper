package com.streamershelper.streamers.service.rapid.api;

import com.streamershelper.streamers.dto.pc.device.CpuDto;
import com.streamershelper.streamers.service.pc.device.CpuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CpuDataFetcher {


    @Value("${x-rapid.api-key}")
    private String xRapidKey;

    @Value("${x-rapid.api-host}")
    private String xRapidHost;

    @Value("${rapid.cpu.url}")
    private String xRapidCpuUrl;

    private final CpuService cpuService;

    private final RestTemplate restTemplate;


    @Async
    public void fetchData() {
        int page = 1;
        while (true) {
            ResponseEntity<CpuDto[]> response = null;
            try {
                response = makeRequest(page);
            } catch (HttpStatusCodeException ex) {
                if (ex.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                    // Если сервер возвращает код 429, ждем 1.5 минуты
                    try {
                        Thread.sleep(90000); // 1.5 минуты
                        continue; // Повторяем тот же запрос после ожидания
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    throw new RuntimeException("Не удалось получить данные. Код ответа: " + ex.getStatusCode());
                }
            }

            if (response != null && response.getStatusCode() == HttpStatus.OK) {
                CpuDto[] cpus = response.getBody();
                if (cpus.length == 0) break;

                cpuService.saveAllCpu(List.of(cpus));
            }
            page++;
        }
    }

    private ResponseEntity<CpuDto[]> makeRequest(int page) {
        HttpHeaders headers;
        headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", xRapidKey);
        headers.set("X-RapidAPI-Host", xRapidHost);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(xRapidCpuUrl)
                .queryParam("page", page);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, CpuDto[].class);
    }
}


