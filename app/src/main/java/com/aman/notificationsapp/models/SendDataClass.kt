package com.aman.notificationsapp.models

data class SendDataClass(var to: String? = null, var data: NotificationData?= null)
data class NotificationData(var title: String? = null, var message: String?= null)
/*{"year": 2023,"month": 1,"day": 26,"hours": 23,"minutes": 20,"timeZone": { "id": "Asia/Calcutta"}}*/
data class targetTime(
    var year:Int?= 0,
    var month:Int?= 0,
    var day:Int?= 0,
    var hours:Int?= 0,
    var minutes:Int?= 0,
    var timeZone:Timezone?= null
)
data class Timezone(var id: String?= null)
