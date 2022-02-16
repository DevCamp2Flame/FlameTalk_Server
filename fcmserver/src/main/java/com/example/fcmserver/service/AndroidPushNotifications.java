package com.example.fcmserver.service;


import com.example.fcmserver.domain.Device;
import com.example.fcmserver.domain.DeviceRepository;
import com.example.fcmserver.dto.PushMessageRequest;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AndroidPushNotifications {


    private final DeviceRepository deviceRepository;

    public String PeriodicNotificationJson(PushMessageRequest pushMessageRequest) throws JSONException, UnsupportedEncodingException {


        List<Device> deviceList = new ArrayList<>();
        for( String userId : pushMessageRequest.getUser_list())
        {
            System.out.println(userId);
            //deviceRepository is null
            List<Device> allDevice = deviceRepository.findByUserId(userId);
            for(Device device : allDevice)
            {
                deviceList.add(device);
            }
        }
        List<String> tokenlist = new ArrayList<String>();
        for(int i=0; i<deviceList.size(); i++){
            tokenlist.add(deviceList.get(i).getToken());
        }
        JSONObject body = new JSONObject();

        JSONArray array = new JSONArray();

        for(int i=0; i<tokenlist.size(); i++) {
            array.put(tokenlist.get(i));
        }
        body.put("registration_ids", array);

        JSONObject dataObject = new JSONObject();
        String nickname = URLEncoder.encode(pushMessageRequest.getNickname(),"UTF-8");
        String contents = URLEncoder.encode(pushMessageRequest.getContents(), "UTF-8");

        dataObject.put("room", pushMessageRequest.getRoom_id());
        dataObject.put("body", contents);
        dataObject.put("title",nickname);
        //body.put("notification", notification);
        body.put("data",dataObject);

        System.out.println(body.toString());

        return body.toString();
    }
}