package com.aman.notificationsapp.models

data class SendDataClass(var to: String? = null, var data: NotificationData?= null)
data class NotificationData(var title: String? = null, var message: String?= null)
