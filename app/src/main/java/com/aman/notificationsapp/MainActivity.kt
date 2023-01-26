package com.aman.notificationsapp

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aman.notificationsapp.databinding.ActivityMainBinding
import com.aman.notificationsapp.models.NotificationData
import com.aman.notificationsapp.models.SendDataClass
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import org.json.JSONObject


open class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private  val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSendNotification.setOnClickListener {
            FirebaseMessaging.getInstance().token
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "FCM registration token failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new FCM registration token
                    val token = task.result
                    Log.d(TAG, token)
                    sendNotification(token)
                })

        }
    }

    override fun onResume() {
        super.onResume()
        checkOverlayPermission()

    }

    fun sendNotification(token: String) {
        var sendMap = SendDataClass(token, NotificationData("Test","1"))
        val json = Gson().toJson(sendMap)
        val jsonObject = JSONObject(json)


        val url = "https://fcm.googleapis.com/fcm/send"
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val jsonObjReq: JsonObjectRequest = object : JsonObjectRequest(
            Method.POST,
            url,
            jsonObject,
            {
                fun onResponse(response: JSONObject) {
                    Log.d(TAG, response.toString())
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError) {
                    VolleyLog.d(
                        TAG, "Error: "
                                + error.localizedMessage
                    )
                }
            }) {
            override fun getBodyContentType(): String? {
                return "application/json"
            }


            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                headers.put("Authorization", "key=AAAA0VP6ckE:APA91bGixF7mMTvOEjcuMjf7SRpXhpbTKJcOlOUznNmbpRa5vYAvnv4kB2NAq6fIYh8YAIg_9ja8GUlGUP3b5aIkEdeVpo2X51u67xdvSLhxqQXCQWmsJxUieofyLHLKYHR0tEXmvrA-")
                return headers
            }

        }

        queue.add(jsonObjReq)
    }

    // method to ask user to grant the Overlay permission
    private fun checkOverlayPermission() {
            if (!Settings.canDrawOverlays(this)) {
                // send user to the device settings
                val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                startActivity(myIntent)
            }

    }
}