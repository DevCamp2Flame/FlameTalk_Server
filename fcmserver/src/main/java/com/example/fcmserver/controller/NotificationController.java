package com.example.fcmserver.controller;


import com.example.fcmserver.dto.PushMessageRequest;
import com.example.fcmserver.service.AndroidPushNotifications;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.example.fcmserver.service.AndroidPushNotificationsService;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    AndroidPushNotifications androidPushNotifications;

    private final ObjectMapper objectMapper;

    //헤더 주입
    @Autowired
    AndroidPushNotificationsService androidPushNotificationsService;


    @PostMapping(value = "/send")
    public ResponseEntity<String> send(
            @RequestBody PushMessageRequest pushMessageRequest
            ) throws JsonProcessingException, UnsupportedEncodingException {

        if(pushMessageRequest.getUser_list().size()==0)
        {
            return new ResponseEntity<>("",HttpStatus.OK);
        }
        else {
            String notifications = androidPushNotifications.PeriodicNotificationJson(pushMessageRequest);
            HttpEntity<String> request = new HttpEntity<>(notifications);

            CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
            CompletableFuture.allOf(pushNotification).join();

            try{
                String firebaseResponse = pushNotification.get();
                return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
            }
            catch (InterruptedException e){
                logger.debug("got interrupted!");
            }
            catch (ExecutionException e){
                logger.debug("execution error!");
            }
        }


        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }
}
