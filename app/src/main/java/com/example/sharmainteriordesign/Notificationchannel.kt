package com.example.sharmainteriordesign

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build


class Notificationchannel(val context: Context){
    val CHANNEL_1:String="Channel1"
    val CHANNEL_2:String="Channel2"
    // @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannels(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val channel1= NotificationChannel(CHANNEL_1,"Channel 1", NotificationManager.IMPORTANCE_HIGH)
            channel1.description="This is channel 1"


            val channel2= NotificationChannel(CHANNEL_2,"Channel 2", NotificationManager.IMPORTANCE_LOW)
            channel2.description="This is channel 2"

            val notificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel2)
            notificationManager.createNotificationChannel(channel1)
        }
    }
}