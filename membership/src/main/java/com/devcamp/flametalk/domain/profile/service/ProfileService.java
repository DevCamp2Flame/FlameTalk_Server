package com.devcamp.flametalk.domain.profile.service;

import com.devcamp.flametalk.domain.profile.domain.Profile;
import com.devcamp.flametalk.domain.profile.domain.ProfileRepository;
import com.devcamp.flametalk.domain.profile.dto.ProfileDetailResponse;
import com.devcamp.flametalk.domain.profile.dto.ProfileRequest;
import com.devcamp.flametalk.domain.user.domain.User;
import com.devcamp.flametalk.domain.user.domain.UserRepository;
import javax.persistence.EntityNotFoundException;
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
        .orElseThrow(() -> new EntityNotFoundException("유저가 존재하지 않습니다."));

    Profile profile = request.toProfile(user);
    profileRepository.save(profile);
    return profile.getId();
  }

  public ProfileDetailResponse findProfile(Long id) {
    Profile profile = profileRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("프로필이 존재하지 않습니다."));
    return ProfileDetailResponse.from(profile);
  }

  @Transactional
  public Long updateProfile(Long profileId, ProfileRequest request) {
    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new EntityNotFoundException("유저가 존재하지 않습니다."));
    Profile profile = profileRepository.findById(profileId)
        .orElseThrow(() -> new EntityNotFoundException("프로필이 존재하지 않습니다."));

    Profile requestProfile = request.toProfile(user);
    Profile updatedProfile = profile.update(requestProfile);
    return updatedProfile.getId();
  }
}
