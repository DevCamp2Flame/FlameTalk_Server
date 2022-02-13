package com.devcamp.flametalk.chatroom.domain.profile.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.persistence.AttributeConverter;

/**
 * Sticker 객체를 DB 테이블에 JSON 형태로 저장하기 위한 Converter 입니다.
 */
public class StickerConverter implements AttributeConverter<List<Sticker>, String> {

  private static final ObjectMapper objectMapper = new ObjectMapper()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  @Override
  public String convertToDatabaseColumn(List<Sticker> attribute) {
    try {
      return objectMapper.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("argument error");
    }
  }

  @Override
  public List<Sticker> convertToEntityAttribute(String dbData) {
    try {
      return Arrays.asList(objectMapper.readValue(dbData, Sticker[].class));
    } catch (IOException e) {
      throw new IllegalArgumentException("error");
    }
  }
}