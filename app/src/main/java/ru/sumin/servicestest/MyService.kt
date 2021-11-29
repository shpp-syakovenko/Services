package ru.sumin.servicestest

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {

    // Service create
    override fun onCreate() {
        super.onCreate()
    }

    // work service
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    // service destroy
    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}