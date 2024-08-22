package com.sparta.springresttemplateclient.service;

import com.sparta.springresttemplateclient.dto.ItemDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    return null;
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