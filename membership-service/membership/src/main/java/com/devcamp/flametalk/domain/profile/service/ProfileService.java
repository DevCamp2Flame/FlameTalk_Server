package com.devcamp.flametalk.domain.profile.service;

import com.devcamp.flametalk.domain.feed.domain.FeedRepository;
import com.devcamp.flametalk.domain.profile.domain.Profile;
import com.devcamp.flametalk.domain.profile.domain.ProfileRepository;
import com.devcamp.flametalk.domain.profile.dto.ProfileDetailResponse;
import com.devcamp.flametalk.domain.profile.dto.ProfileRequest;
import com.devcamp.flametalk.domain.profile.dto.ProfileSimpleResponse;
import com.devcamp.flametalk.domain.profile.dto.ProfilesResponse;
import com.devcamp.flametalk.domain.user.domain.User;
import com.devcamp.flametalk.domain.user.domain.UserRepository;
import com.devcamp.flametalk.global.error.ErrorCode;
import com.devcamp.flametalk.global.error.exception.EntityExistsException;
import com.devcamp.flametalk.global.error.exception.EntityNotFoundException;
import com.devcamp.flametalk.global.error.exception.ForbiddenException;
import java.util.List;
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
    if (request.isDefault() && profileRepository.existsByUserAndIsDefault(user, true)) {
      throw new EntityExistsException(ErrorCode.DEFAULT_PROFILE_EXIST);
    }

    Profile profile = request.toProfile(user);
    profileRepository.save(profile);

    saveImageToFeed(request, profile);
    saveBackgroundImageToFeed(request, profile);

    return profile.getId();
  }

  private void saveImageToFeed(ProfileRequest request, Profile profile) {
    if (request.getImageUrl() != null) {
      feedRepository.save(request.imageToFeed(profile));
    }
  }

  private void saveBackgroundImageToFeed(ProfileRequest request, Profile profile) {
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
   * 유저가 보유한 프로필을 모두 조회합니다.
   *
   * @param id 유저 ID
   * @return 유저가 보유한 모든 프로필 정보
   */
  public ProfilesResponse findByUserId(String id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    List<Profile> profiles = profileRepository.getAllByUser(user);
    return ProfilesResponse.of(user, ProfileSimpleResponse.createList(profiles));
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

    if (profile.getImageUrl() == null || !profile.getImageUrl().equals(request.getImageUrl())) {
      saveImageToFeed(request, profile);
    }
    if (profile.getBgImageUrl() == null || !profile.getBgImageUrl()
        .equals(request.getBgImageUrl())) {
      saveBackgroundImageToFeed(request, profile);
    }

    Profile requestProfile = request.toProfile(user);
    Profile updatedProfile = profile.update(requestProfile);
    return updatedProfile.getId();
  }

  /**
   * id에 해당하는 프로필을 삭제합니다. 해당 프로필을 참조하는 피드도 삭제됩니다.
   *
   * @param id 프로필 id
   */
  @Transactional
  public void deleteById(Long id) {
    Profile profile = profileRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.PROFILE_NOT_FOUND));
    if (Boolean.TRUE.equals(profile.getIsDefault())) {
      throw new ForbiddenException(ErrorCode.DELETE_FORBIDDEN_PROFILE);
    }
    profileRepository.delete(profile);
  }
}
