package com.infidelrahul.antigravitymobile.runtime
import android.app.*
import android.content.*
import android.os.IBinder
class LinuxRuntimeService:Service(){override fun onCreate(){super.onCreate();val n=getSystemService(NotificationManager::class.java);n.createNotificationChannel(NotificationChannel("runtime","Linux runtime",NotificationManager.IMPORTANCE_LOW));startForeground(4400,Notification.Builder(this,"runtime").setContentTitle("Antigravity").setContentText("Linux runtime is available").setSmallIcon(android.R.drawable.stat_notify_sync_noanim).setOngoing(true).build())}override fun onBind(i:Intent?):IBinder?=null
companion object{fun start(c:Context){c.startForegroundService(Intent(c,LinuxRuntimeService::class.java))}}}
