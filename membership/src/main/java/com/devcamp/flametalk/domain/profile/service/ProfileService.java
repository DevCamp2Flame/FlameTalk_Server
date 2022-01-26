package com.devcamp.flametalk.domain.profile.service;

import com.devcamp.flametalk.domain.profile.domain.Profile;
import com.devcamp.flametalk.domain.profile.domain.ProfileRepository;
import com.devcamp.flametalk.domain.profile.dto.ProfileDetailResponse;
import com.devcamp.flametalk.domain.profile.dto.ProfileRequest;
import com.devcamp.flametalk.domain.profile.error.ErrorCode;
import com.devcamp.flametalk.domain.profile.error.exception.EntityNotFoundException;
import com.devcamp.flametalk.domain.user.domain.User;
import com.devcamp.flametalk.domain.user.domain.UserRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileService {

  private final ProfileRepository profileRepository;
  private final UserRepository userRepository;

  @Transactional
  public Long save(ProfileRequest request) {
    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

    Profile profile = request.toProfile(user);
    profileRepository.save(profile);
    return profile.getId();
  }

  public ProfileDetailResponse findProfile(Long id) {
    Profile profile = profileRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.PROFILE_NOT_FOUND));
    return ProfileDetailResponse.from(profile);
  }

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
