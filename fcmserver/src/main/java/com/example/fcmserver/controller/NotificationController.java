package com.example.fcmserver.controller;


import com.example.fcmserver.dto.PushMessageRequest;
import com.example.fcmserver.service.AndroidPushNotifications;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.example.fcmserver.service.AndroidPushNotificationsService;

@RestController
public class NotificationController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    //헤더 주입
    @Autowired
    AndroidPushNotificationsService androidPushNotificationsService;

    @GetMapping(value = "/send")
    public @ResponseBody ResponseEntity<String> send(
            @RequestBody PushMessageRequest pushMessageRequest
            ) throws JSONException, InterruptedException  {

        String notifications = AndroidPushNotifications.PeriodicNotificationJson(pushMessageRequest);

        HttpEntity<String> request = new HttpEntity<>(notifications);

        CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try{
            String firebaseResponse = pushNotification.get();
            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        }
        catch (InterruptedException e){
            logger.debug("got interrupted!");
            throw new InterruptedException();
        }
        catch (ExecutionException e){
            logger.debug("execution error!");
        }

        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }
}
