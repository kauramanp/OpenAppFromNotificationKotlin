package com.aman.notificationsapp.services

import android.content.Intent
import android.provider.Settings
import android.util.Log
import com.aman.notificationsapp.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseService :FirebaseMessagingService() {
    private  val TAG = "FirebaseService"
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.e(TAG,"data ${message.data}")

        if(message.data != null){
            var map : Map<String, String> = message.data
            if(map["message"] == "1"){
                Log.e(TAG," in condition")
                if (Settings.canDrawOverlays(this@FirebaseService)) {
                    val intent = Intent(this@FirebaseService, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    this.startActivity(intent)
                }
            }
        }



    }
}