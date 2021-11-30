package ru.sumin.servicestest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ru.sumin.servicestest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

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
    }

}
