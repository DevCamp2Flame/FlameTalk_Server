package com.example.fcmserver.domain;

import com.google.firebase.database.annotations.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JoinColumn(name= "user_id")
    private String userId;

    private String device_id;

    private String token;

    private String cur_max_message_id;

    @Builder
    public Device(Long id, String user_id, String device_id, String token, String cur_max_message_id) {
        this.id = id;
        this.userId = user_id;
        this.device_id = device_id;
        this.token = token;
        this.cur_max_message_id = cur_max_message_id;
    }


}
