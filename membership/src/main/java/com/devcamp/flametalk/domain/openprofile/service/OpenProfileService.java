package com.devcamp.flametalk.domain.openprofile.service;

import com.devcamp.flametalk.domain.openprofile.domain.OpenProfile;
import com.devcamp.flametalk.domain.openprofile.domain.OpenProfileRepository;
import com.devcamp.flametalk.domain.openprofile.dto.OpenProfileCreateRequest;
import com.devcamp.flametalk.domain.openprofile.dto.OpenProfileDetailResponse;
import com.devcamp.flametalk.domain.user.domain.User;
import com.devcamp.flametalk.domain.user.domain.UserRepository;
import com.devcamp.flametalk.global.error.ErrorCode;
import com.devcamp.flametalk.global.error.exception.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * OpenProfile API 로직을 관리하는 Service 입니다.
 */
@RequiredArgsConstructor
@Service
public class OpenProfileService {

  private final OpenProfileRepository openProfileRepository;
  private final UserRepository userRepository;

  /**
   * 오픈 프로필을 DB에 생성합니다.
   *
   * @param request 오픈 프로필에 생성할 정보
   * @return 저장된 오픈 프로필 상세 정보
   */
  @Transactional
  public OpenProfileDetailResponse create(OpenProfileCreateRequest request) {
    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    // TODO: token userId와 비교

    OpenProfile openProfile = request.toOpenProfile(user);
    openProfileRepository.save(openProfile);

    return OpenProfileDetailResponse.from(openProfile);
  }
}
