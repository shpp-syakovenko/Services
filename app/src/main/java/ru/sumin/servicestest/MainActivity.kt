package ru.sumin.servicestest

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobService
import android.app.job.JobWorkItem
import android.content.ComponentName
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ru.sumin.servicestest.MyJobService.Companion.JOB_SERVICE_ID
import ru.sumin.servicestest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var page = 0

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.simpleService.setOnClickListener {
            // Destroy service MyForegroundService
            stopService(MyForegroundService.newInstance(this@MainActivity))

            //startService(MyService.newInstance(this))
            //Toast.makeText(this@MainActivity, "Start", Toast.LENGTH_SHORT).show()
        }

        binding.foregroundService.setOnClickListener {
            ContextCompat.startForegroundService(this@MainActivity, MyForegroundService.newInstance(this))
            Toast.makeText(this@MainActivity, "Start", Toast.LENGTH_SHORT).show()
        }

        binding.intentService.setOnClickListener {
            Toast.makeText(this@MainActivity, "Start", Toast.LENGTH_SHORT).show()
            ContextCompat.startForegroundService(this, MyIntentService.newInstance(this))
        }

        binding.jobScheduler.setOnClickListener {
            Toast.makeText(this@MainActivity, "Start", Toast.LENGTH_SHORT).show()

            // Указываем какой сервис нужен
            val componentName = ComponentName(this, MyJobService::class.java)

            // Устанавливаем ограничения, телефон подключен к зарядке, подключен к wifi, на перезагрузку телефона
            val jobInfo = JobInfo.Builder(JOB_SERVICE_ID, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .build()

            // запускаем на выполнение сервис
            val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            //jobScheduler.schedule(jobInfo)

            // Запускаем сервисы которые будут выполнятся в очереди
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val intent = MyJobService.newIntent(page++)
                jobScheduler.enqueue(jobInfo, JobWorkItem(intent))
            }else{
                startService(MyIntentService2.newInstance(this, page++))
            }
        }

        binding.jobIntentService.setOnClickListener {
            MyJobIntentService.enqueue(this, page++)
        }


    }

}
