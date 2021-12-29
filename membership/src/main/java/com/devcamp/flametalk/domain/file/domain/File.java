package com.devcamp.flametalk.domain.file.domain;

import com.devcamp.flametalk.domain.profile.domain.Profile;
import com.devcamp.flametalk.domain.user.domain.User;
import com.devcamp.flametalk.model.BaseTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Entity
public class File extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String content;

    @NotNull
    private String type;

    private String thumbnail;

    @OneToOne(mappedBy = "image")
    private Profile profileImage;

    @OneToOne(mappedBy = "backgroundImage")
    private Profile profileBackgroundImage;

    @OneToOne(mappedBy = "stickerImage")
    private Profile profileStickerImage;
}
