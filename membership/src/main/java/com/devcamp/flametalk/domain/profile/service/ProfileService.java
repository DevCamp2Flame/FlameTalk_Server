package com.devcamp.flametalk.domain.profile.service;

import com.devcamp.flametalk.domain.file.domain.File;
import com.devcamp.flametalk.domain.file.domain.FileRepository;
import com.devcamp.flametalk.domain.profile.domain.Profile;
import com.devcamp.flametalk.domain.profile.domain.ProfileRepository;
import com.devcamp.flametalk.domain.profile.dto.ProfileCreateRequest;
import com.devcamp.flametalk.domain.profile.dto.ProfileResponse;
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
    private final FileRepository fileRepository;

    @Transactional
    public Long save(ProfileCreateRequest request) {
        // TODO: 인가 로직 수정
        User user = userRepository.getById(request.getUserId());


        File image = getFileById(request.getImageId());
        File backgroundImage = getFileById(request.getBackgroundImageId());
        File sticker = getFileById(request.getStickerId());

        Profile profile = request.toProfile(user, image, backgroundImage, sticker);
        profileRepository.save(profile);

        return profile.getId();
    }

    private File getFileById(Long id) {
        return fileRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("파일이 존재하지 않습니다."));
    }

    public ProfileResponse findProfile(Long id) {
        Profile profile = profileRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("프로필이 존재하지 않습니다."));
        return ProfileResponse.of(profile);
    }
}
