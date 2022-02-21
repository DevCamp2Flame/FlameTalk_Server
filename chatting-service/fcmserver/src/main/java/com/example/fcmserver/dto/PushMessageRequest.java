package com.example.fcmserver.dto;

import com.google.firebase.database.annotations.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
@ToString
@AllArgsConstructor
@Getter
public class PushMessageRequest {

    @NotNull
    private List<String> user_list;
    @NotNull
    private String message_id;

    @NotNull
    private String room_id;

    @NotNull
    private String sender_id;

    @NotNull
    private String nickname;

    private String contents;

    private String file_url;

}
