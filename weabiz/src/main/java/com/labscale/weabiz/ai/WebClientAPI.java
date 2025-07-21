package com.labscale.weabiz.ai;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientAPI {

    private static final Logger log = LoggerFactory.getLogger(WebClientAPI.class);

    private final WebClient webClient;

    public WebClientAPI() {
        this.webClient = WebClient.builder()
                .baseUrl("http://127.0.0.1:8000")
                .build();
    }

    public List<String> ask(String prompt) {
        try {
            return webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/board/summary")
                            .queryParam("prompt", prompt)
                            .build())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<String>>() {})
                    .block();
        } catch (Exception e) {
            log.error("LLM API 호출 중 오류 발생: {}", e.getMessage());
            return List.of("요약을 가져오는 데 실패했습니다.");
        }
    }
}
