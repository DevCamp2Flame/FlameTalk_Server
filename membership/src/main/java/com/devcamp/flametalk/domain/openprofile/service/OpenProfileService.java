package com.devcamp.flametalk.domain.openprofile.service;

import com.devcamp.flametalk.domain.openprofile.domain.OpenProfile;
import com.devcamp.flametalk.domain.openprofile.domain.OpenProfileRepository;
import com.devcamp.flametalk.domain.openprofile.dto.OpenProfileCreateRequest;
import com.devcamp.flametalk.domain.openprofile.dto.OpenProfileDetailResponse;
import com.devcamp.flametalk.domain.openprofile.dto.OpenProfileUpdateRequest;
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
  public OpenProfileDetailResponse save(OpenProfileCreateRequest request) {
    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    // TODO: token userId와 비교

    OpenProfile openProfile = request.toOpenProfile(user);
    openProfileRepository.save(openProfile);

    return OpenProfileDetailResponse.from(openProfile);
  }

  /**
   * id에 해당하는 오픈 프로필의 상세정보를 조회합니다.
   *
   * @param id DB에 저장된 오픈 프로필 ID
   * @return 조회된 오픈 프로필 상세 정보
   */
  public OpenProfileDetailResponse findById(Long id) {
    OpenProfile openProfile = openProfileRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.OPEN_PROFILE_NOT_FOUND));
    return OpenProfileDetailResponse.from(openProfile);
  }

  /**
   * DB에 저장된 오픈 프로필 정보를 업데이트합니다.
   *
   * @param id      업데이트할 오픈 프로필 id
   * @param request 업데이트할 오픈 프로필 상세 정보
   * @return 업데이트된 오픈 프로필 상세 정보
   */
  @Transactional
  public OpenProfileDetailResponse updateOpenProfile(Long id, OpenProfileUpdateRequest request) {
    OpenProfile openProfile = openProfileRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.OPEN_PROFILE_NOT_FOUND));
    // TODO: token userId로 권한 및 유저 엔티티 존재 확인

    OpenProfile requestOpenProfile = request.toOpenProfile(openProfile.getOpenProfileUser());
    OpenProfile updatedOpenProfile = openProfile.update(requestOpenProfile);
    return OpenProfileDetailResponse.from(updatedOpenProfile);
  }

  /**
   * id에 해당하는 오픈 프로필을 삭제합니다.
   *
   * @param id 오픈 프로필 id
   */
  @Transactional
  public void deleteById(Long id) {
    OpenProfile openProfile = openProfileRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.OPEN_PROFILE_NOT_FOUND));
    openProfileRepository.delete(openProfile);
  }
}
