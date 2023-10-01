package com.nour.centerapp;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

// this class to check that message it's token and received or not
public class MessageService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String tokenMessage) {
        super.onNewToken(tokenMessage);
        Log.d("FCM","token:"+tokenMessage);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification()!=null)
        {
            Log.d("FCM ","RemoteMessage"+remoteMessage.getNotification().getBody());
        }
    }
}