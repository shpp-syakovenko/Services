package ru.sumin.servicestest

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import android.os.PersistableBundle
import android.util.Log
import kotlinx.coroutines.*

class MyJobService : JobService() {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    // Service create
    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    // Если по окончанию работы метода сервис еще выполняется (в фоновом потоке к примеру)
    // возвращаем true и вручную указываем когда закончится выполнение сервиса при помощи метода
    // jobFinished()
    override fun onStartJob(params: JobParameters?): Boolean {
        log("onStartJob")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            scope.launch {

                // Достаем сервис из очереди
                var workItem = params?.dequeueWork()

                // Если сервисов в очереди больше нет вернет null
                while (workItem != null) {
                    val page = workItem.intent.getIntExtra(PAGE, 0)

                    for (i in 0 until 5) {
                        log("Page: $page $i")
                        delay(1000)
                    }

                    // сервис закончил работу
                    params?.completeWork(workItem)
                    // достаем новый сервис из очереди
                    workItem = params?.dequeueWork()
                }
                // если после окончания работы сервиса нужно перезапустить сервис то передаем true иначе false
                // метод окончательно уничтожает сервис даже если есть сервисы в очереди
                jobFinished(params, false)
            }
        }
        return true
    }

    // После того как система убила наш сервис его нужно перезапустить нужно вернуть true иначе false
    override fun onStopJob(p0: JobParameters?): Boolean {
        log("onStopJob")
        return true
    }

    // service destroy
    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        scope.cancel()
    }

    private fun log(message: String) {
        Log.d("JobService:", message)
    }

    companion object {
        const val JOB_SERVICE_ID = 10
        const val PAGE = "page"

        fun newIntent(page: Int): Intent {
            return Intent().apply {
                putExtra(PAGE, page)
            }
        }

        fun newBundle(page: Int): PersistableBundle {
            return PersistableBundle().apply {
                putInt(PAGE, page)
            }
        }
    }

}