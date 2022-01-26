package com.devcamp.flametalk.domain.profile.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.persistence.AttributeConverter;

/**
 * Sticker 객체를 DB 테이블에 JSON 형태로 저장하기 위한 Converter 입니다.
 */
public class StickerConverter implements AttributeConverter<Sticker, String> {

  private static final ObjectMapper objectMapper = new ObjectMapper()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  @Override
  public String convertToDatabaseColumn(Sticker attribute) {
    try {
      return objectMapper.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("argument error");
    }
  }

  @Override
  public Sticker convertToEntityAttribute(String dbData) {
    try {
      return objectMapper.readValue(dbData, Sticker.class);
    } catch (IOException e) {
      throw new IllegalArgumentException("error");
    }
  }
}
