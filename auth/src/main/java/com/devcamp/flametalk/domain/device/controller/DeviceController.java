package com.devcamp.flametalk.domain.device.controller;

import com.devcamp.flametalk.domain.device.dto.DeviceTokenRequestDto;
import com.devcamp.flametalk.domain.device.dto.DeviceTokenResponseDto;
import com.devcamp.flametalk.domain.device.dto.MessageRequestDto;
import com.devcamp.flametalk.domain.device.dto.MessageResponseDto;
import com.devcamp.flametalk.domain.device.service.DeviceService;
import com.devcamp.flametalk.global.response.DefaultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자 기기와 관련된 컨트롤러입니다.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/device")
public class DeviceController {

  private final DeviceService deviceService;

  @PostMapping("/token")
  public ResponseEntity<DefaultResponse<DeviceTokenResponseDto>> saveDeviceToken(
      @RequestHeader("USER-ID") String userId, @RequestHeader("DEVICE-ID") String deviceId,
      @RequestBody DeviceTokenRequestDto deviceTokenRequestDto) {
    return deviceService.saveDeviceToken(userId, deviceId, deviceTokenRequestDto);
  }

  @PostMapping("/message")
  public ResponseEntity<DefaultResponse<MessageResponseDto>> updateMessageId(
      @RequestHeader("USER-ID") String userId, @RequestHeader("DEVICE-ID") String deviceId,
      @RequestBody MessageRequestDto messageRequestDto) {
    return deviceService.updateMessageId(userId, deviceId, messageRequestDto);
  }
}