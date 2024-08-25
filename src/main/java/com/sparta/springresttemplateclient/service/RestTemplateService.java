package com.sparta.springresttemplateclient.service;

import com.sparta.springresttemplateclient.dto.ItemDto;
import com.sparta.springresttemplateclient.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
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

    log.info("uri: " + uri);

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
    // 요청 URL 만들기
    URI uri = UriComponentsBuilder
            .fromUriString("http://localhost:7070")
            .path("/api/server/get-call-list")
            .encode()
            .build()
            .toUri();

    log.info("uri: " + uri);

    // data 가 String 형식으로 저장됨
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

    log.info("statusCode: " + responseEntity.getStatusCode());
    log.info("responseBody: " + responseEntity.getBody());

    return fromJSONtoItems(responseEntity.getBody());
  }

  public ItemDto postCall(String query) {
    // 요청 URL 만들기
    URI uri = UriComponentsBuilder
            .fromUriString("http://localhost:7070")
            .path("/api/server/post-call/{query}")  // PathVariable 방식
            .encode().build()
            .expand(query)  // { } 값 동적으로 처리
            .toUri();

    log.info("uri: " + uri);

    User user = new User("Robbie", "1234");
    // Post 방식으로 해당 URI 의 서버에 요청
    ResponseEntity<ItemDto> responseEntity = restTemplate.postForEntity(uri, user, ItemDto.class);

    log.info("statusCode: " + responseEntity.getStatusCode());

    return responseEntity.getBody();
  }

  public List<ItemDto> exchangeCall(String token) {
    return null;
  }

  public List<ItemDto> fromJSONtoItems(String responseEntity) {
    // String -> JSONObject
    JSONObject jsonObject = new JSONObject(responseEntity);
    // JSONObject -> item 배열
    JSONArray items = jsonObject.getJSONArray("items");

    // JSONArray 를 for 문을 돌면서 ItemDto 로 변환
    List<ItemDto> itemDtoList = new ArrayList<>();

    for (Object item : items) {
      ItemDto itemDto = new ItemDto((JSONObject) item);
      itemDtoList.add(itemDto);
    }

    return itemDtoList;
  }
}