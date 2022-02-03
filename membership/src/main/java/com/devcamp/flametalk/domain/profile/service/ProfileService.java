package com.devcamp.flametalk.domain.profile.service;

import com.devcamp.flametalk.domain.feed.domain.FeedRepository;
import com.devcamp.flametalk.domain.profile.domain.Profile;
import com.devcamp.flametalk.domain.profile.domain.ProfileRepository;
import com.devcamp.flametalk.domain.profile.dto.ProfileDetailResponse;
import com.devcamp.flametalk.domain.profile.dto.ProfileRequest;
import com.devcamp.flametalk.global.error.ErrorCode;
import com.devcamp.flametalk.global.error.exception.EntityNotFoundException;
import com.devcamp.flametalk.domain.user.domain.User;
import com.devcamp.flametalk.domain.user.domain.UserRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Profile API 로직을 관리하는 Service 입니다.
 */
@RequiredArgsConstructor
@Service
public class ProfileService {

  private final ProfileRepository profileRepository;
  private final UserRepository userRepository;
  private final FeedRepository feedRepository;

  /**
   * 프로필을 DB에 생성합니다.
   *
   * @param request 프로필에 생성할 정보
   * @return DB에 저장된 프로필 id
   */
  @Transactional
  public Long save(ProfileRequest request) {
    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

    Profile profile = request.toProfile(user);
    profileRepository.save(profile);

    saveProfileToFeed(request, profile);

    return profile.getId();
  }

  private void saveProfileToFeed(ProfileRequest request, Profile profile) {
    if (request.getImageUrl() != null) {
      feedRepository.save(request.imageToFeed(profile));
    }
    if (request.getBgImageUrl() != null) {
      feedRepository.save(request.bgImageToFeed(profile));
    }
  }

  /**
   * DB에 저장된 프로필의 상세정보를 조회합니다.
   *
   * @param id DB에 저장된 프로필 ID
   * @return 조회된 프로필 상세 정보
   */
  public ProfileDetailResponse findProfile(Long id) {
    Profile profile = profileRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.PROFILE_NOT_FOUND));
    return ProfileDetailResponse.from(profile);
  }

  /**
   * DB에 저장된 프로필 정보를 업데이트합니다.
   *
   * @param profileId 업데이트할 프로필 id
   * @param request   업데이트할 프로필 상세 정보
   * @return 업데이트된 프로필 id
   */
  @Transactional
  public Long updateProfile(Long profileId, ProfileRequest request) {
    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    Profile profile = profileRepository.findById(profileId)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.PROFILE_NOT_FOUND));

    Profile requestProfile = request.toProfile(user);
    Profile updatedProfile = profile.update(requestProfile);
    return updatedProfile.getId();
  }
}
