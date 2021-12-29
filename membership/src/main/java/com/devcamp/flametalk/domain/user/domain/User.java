package com.devcamp.flametalk.domain.user.domain;

import com.devcamp.flametalk.domain.profile.domain.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class User {
    @Type(type="uuid-char")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Id
    private UUID id;

    private String password;

    @NotNull
    private String email;

    @NotNull
    private String number;

    @NotNull
    private String nickname;

    @NotNull
    private String birthday;

    @NotNull
    private boolean isSocial;

    @OneToMany(mappedBy = "user")
    private List<Profile> profiles = new ArrayList<>();
}
