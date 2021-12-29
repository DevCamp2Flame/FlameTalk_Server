package com.devcamp.flametalk.domain.profile.domain;

import com.devcamp.flametalk.domain.file.domain.File;
import com.devcamp.flametalk.domain.user.domain.User;
import com.devcamp.flametalk.model.BaseTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Profile extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "image_id")
    private File image;

    @OneToOne
    @JoinColumn(name = "background_image_id")
    private File backgroundImage;

    @OneToOne
    @JoinColumn(name = "sticker_image_id")
    private File stickerImage;

    private int StickerPositionX;

    private int StickerPositionY;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private boolean isDefault;
}
