package com.example.fcmserver.service;


import com.example.fcmserver.dto.PushMessageRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AndroidPushNotifications {

    public static String PeriodicNotificationJson(PushMessageRequest pushMessageRequest) throws JSONException {
        LocalDate localDate = LocalDate.now();
        //device token list
        String sampleData[] = pushMessageRequest.getUser_list().toArray(new String[0]);


        JSONObject body = new JSONObject();
        List<String> tokenlist = new ArrayList<String>();

        for(int i=0; i<sampleData.length; i++){
            tokenlist.add(sampleData[i]);
        }

        JSONArray array = new JSONArray();

        for(int i=0; i<tokenlist.size(); i++) {
            array.put(tokenlist.get(i));
        }

        body.put("registration_ids", array);

        JSONObject notification = new JSONObject();
        //title 유저 이름
        notification.put("title",pushMessageRequest.getNickname());
        // 채팅 메시지
        if(pushMessageRequest.getContents() != "")
        {
            notification.put("body",pushMessageRequest.getContents());
        }else {
            notification.put("body",pushMessageRequest.getFile_url());
        }
        body.put("data", notification);
        System.out.println(body.toString());

        return body.toString();
    }
}