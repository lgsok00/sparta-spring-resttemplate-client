package com.sparta.springresttemplateclient.service;

import com.sparta.springresttemplateclient.dto.ItemDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
public class RestTemplateService {

  // RestTemplate 주입
  private final RestTemplate restTemplate;

  public RestTemplateService(RestTemplateBuilder builder) {
    this.restTemplate = builder.build();
  }

  public ItemDto getCallObject(String query) {
    // UriComponentsBuilder : 요청 URI 만들기
    URI uri = UriComponentsBuilder
            .fromUriString("http://localhost:7070")  // 서버 URI
            .path("/api/server/get-call-obj")  // 서버 Controller URI
            .queryParam("query", query)
            .encode().build()
            .toUri();

    log.info("uri = " + uri);

    /*
      - getForEntity : GET 방식으로 해당 URI 요청 진행
          - 첫 번째 파라미터 : URI
          - 두 번째 파라미터 : Item 정보
     */
    ResponseEntity<ItemDto> responseEntity = restTemplate.getForEntity(uri, ItemDto.class);

    log.info("statusCode: " + responseEntity.getStatusCode());

    return responseEntity.getBody();
  }

  public List<ItemDto> getCallList() {
    return null;
  }

  public ItemDto postCall(String query) {
    return null;
  }

  public List<ItemDto> exchangeCall(String token) {
    return null;
  }
}