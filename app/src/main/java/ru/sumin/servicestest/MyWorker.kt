package ru.sumin.servicestest

import android.content.Context
import android.util.Log
import androidx.work.*

class MyWorker(context: Context, private val workerParameters: WorkerParameters) : Worker(
    context,
    workerParameters
) {
    // Выполняется в фоновом потоке
    override fun doWork(): Result {
        log("doWork")
        val page = workerParameters.inputData.getInt(PAGE, 0)
        for (i in 0 until 5) {

            Thread.sleep(1000)
            log("Timer $i $page")
        }
        return Result.success()
    }


    private fun log(message: String) {
        Log.d("SERVICE_TAG:", message)
    }

    companion object {

        private const val PAGE = "page"
        const val WORK_NAME = "workName"

        // Создаем обькет типа OneTimeWorkRequest куда мы можем положить какието данные и установить ограничения
        fun makeRequest(page: Int): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<MyWorker>()
                .setInputData(workDataOf(PAGE to page))
                .setConstraints(makeConstraints())
                .build()
        }

        // Установка огранечений по работе сервиса
        private fun makeConstraints(): Constraints {
            return Constraints.Builder()
                .setRequiresCharging(true) // Работа при зарядки телефона
                .setRequiredNetworkType(NetworkType.UNMETERED) // Работает только при подключении wifi
                .build()
        }
    }
}