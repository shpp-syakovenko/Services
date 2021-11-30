package ru.sumin.servicestest

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class MyJobIntentService : JobIntentService() {


    // Service create
    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onHandleWork(intent: Intent) {
        log("onHandleWork")
        val page = intent.getIntExtra(PAGE, 0)
        for (i in 0 until 5) {
            Thread.sleep(1000)
            log("Timer $i $page")
        }
    }

    // service destroy
    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG:", message)
    }


    companion object {

        private const val PAGE = "page"
        private const val JOB_ID = 111

        // Start service
        fun enqueue(context: Context, page: Int){
            enqueueWork(
                context,
                MyJobIntentService::class.java,
                JOB_ID,
                newInstance(context, page)
            )
        }

        private fun newInstance(context: Context, page: Int): Intent {
            return Intent(context, MyJobIntentService::class.java).apply {
                putExtra(PAGE, page)
            }
        }
    }
}