package ru.sumin.servicestest

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyService : Service() {

    private val scope:CoroutineScope = CoroutineScope(Dispatchers.Main)

    // Service create
    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    // work service
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand")

        scope.launch {
            for (i in 0 until 100){
                log(i.toString())
                delay(1000)
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    // service destroy
    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        scope.cancel()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(message: String){
        Log.d("SERVICE_TAG:", message)
    }

    companion object{
        fun newInstance(context: Context): Intent{
            return Intent(context, MyService::class.java)
        }
    }
}