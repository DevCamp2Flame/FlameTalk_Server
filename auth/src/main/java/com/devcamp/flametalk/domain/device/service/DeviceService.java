package com.devcamp.flametalk.domain.device.service;

import static com.devcamp.flametalk.global.response.StatusCode.MISMATCH_DEVICE;
import static com.devcamp.flametalk.global.response.StatusCode.SUCCESS_SAVE_TOKEN;
import static com.devcamp.flametalk.global.response.StatusCode.SUCCESS_UPDATE_MESSAGEID;

import com.devcamp.flametalk.domain.device.domain.Device;
import com.devcamp.flametalk.domain.device.domain.DeviceRepository;
import com.devcamp.flametalk.domain.device.dto.DeviceTokenRequestDto;
import com.devcamp.flametalk.domain.device.dto.DeviceTokenResponseDto;
import com.devcamp.flametalk.domain.device.dto.MessageRequestDto;
import com.devcamp.flametalk.domain.device.dto.MessageResponseDto;
import com.devcamp.flametalk.global.response.DefaultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Device 의 비즈니스 로직을 처리하는 클래스입니다.
 */
@RequiredArgsConstructor
@Service
public class DeviceService {

  private final DeviceRepository deviceRepository;

  /**
   * FCM 메시지를 보내기 위한 device token 을 저장합니다.
   *
   * @param userId                사용자 id
   * @param deviceId              디바이스 id
   * @param deviceTokenRequestDto FCM 에 등록된 디바이스 token
   * @return 갱신된 사용자, 기기, token 정보
   */
  public ResponseEntity<DefaultResponse<DeviceTokenResponseDto>> saveDeviceToken(String userId,
      String deviceId, DeviceTokenRequestDto deviceTokenRequestDto) {

    Device findDevice = deviceRepository.findByDeviceIdAndUserId(deviceId, userId).orElse(null);
    // deviceId 가 올바르지 않은 경우 에러 메시지 반환
    if (findDevice == null) {
      return DefaultResponse.toResponseEntity(HttpStatus.OK, MISMATCH_DEVICE,
          new DeviceTokenResponseDto());
    }

    // deviceToken 저장
    findDevice.setDeviceToken(deviceTokenRequestDto.getDeviceToken());
    Device device = deviceRepository.save(findDevice);

    return DefaultResponse.toResponseEntity(HttpStatus.OK, SUCCESS_SAVE_TOKEN,
        new DeviceTokenResponseDto(device.getUser().getId(), device.getDeviceId(),
            device.getToken()));
  }

  /**
   * 해당 기기에서 읽은 마지막 메시지 id 를 저장합니다.
   *
   * @param userId            사용자 id
   * @param deviceId          디바이스 id
   * @param messageRequestDto 마지막으로 읽은 메시지 id
   * @return 갱신된 사용자, 기기, 메시지 정보
   */
  public ResponseEntity<DefaultResponse<MessageResponseDto>> updateMessageId(String userId,
      String deviceId, MessageRequestDto messageRequestDto) {

    Device findDevice = deviceRepository.findByDeviceIdAndUserId(deviceId, userId).orElse(null);
    // deviceId 가 올바르지 않은 경우 에러 메시지 반환
    if (findDevice == null) {
      return DefaultResponse.toResponseEntity(HttpStatus.OK, MISMATCH_DEVICE,
          new MessageResponseDto());
    }

    // 해당 디바이스에서 마지막으로 읽은 메시지 갱신
    findDevice.setCurMaxMessageId(messageRequestDto.getCurMaxMesssageId());
    Device device = deviceRepository.save(findDevice);

    return DefaultResponse.toResponseEntity(HttpStatus.OK, SUCCESS_UPDATE_MESSAGEID,
        new MessageResponseDto(device.getUser().getId(), device.getDeviceId(),
            device.getCurMaxMessageId()));
  }
}