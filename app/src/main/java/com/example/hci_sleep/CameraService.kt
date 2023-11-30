package com.example.hci_sleep

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.app.NotificationCompat

class CameraService : Service() {
    companion object {
        private const val TAG = "MyServiceTag"

        // Notification
        private const val NOTI_ID = 1
    }
    override fun onBind(intent: Intent): Nothing? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotification()
        mThread!!.start()
        Log.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mThread != null) {
            mThread!!.interrupt()
            mThread = null
        }
        Log.d(TAG, "onDestroy")
    }

    private fun createNotification() {
        val builder = NotificationCompat.Builder(this, "default")
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentTitle("Foreground Service")
        builder.setContentText("포그라운드 서비스")
        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        builder.setContentIntent(pendingIntent) // 알림 클릭 시 이동

        // 알림 표시
        val notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    "default",
                    "기본 채널",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }
        notificationManager.notify(1, builder.build()) // id : 정의해야하는 각 알림의 고유한 int값
        val notification = builder.build()
        startForeground(1, notification)
    }

    private var mThread: Thread? = object : Thread("My Thread") {
        override fun run() {
            super.run()
            for (i in 0..99) {
                Log.d(TAG, "count : $i")
                try {
                    sleep(1000)
                } catch (e: InterruptedException) {
                    currentThread().interrupt()
                    break
                }
            }
        }
    }
}