package com.example.countdowntimerapplication

import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.example.countdowntimerapplication.databinding.ActivityMainBinding
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
   lateinit var dateFormat: SimpleDateFormat
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        var calendar = Calendar.getInstance()
        dateFormat = SimpleDateFormat("EEE, MMM d")
        var date = dateFormat.format(calendar.time)
        binding.changeSwitch.setOnCheckedChangeListener { _,checkedId ->
            run {
                when (checkedId) {
                    true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
        binding.tvDate.text = date
         fun startTimer(minute: Int) {
            var countDownTimer = object : CountDownTimer((60 * minute * 1000).toLong(), 500) {
                // 500 means, onTick function will be called at every 500 milliseconds
                override fun onTick(leftTimeInMilliseconds: Long) {
                    val seconds = leftTimeInMilliseconds / 1000
                    binding.barTimer.progress = seconds.toInt()
                    binding.tvTimer.text = String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60)
                    // format the textview to show the easily readable format
                }
                override fun onFinish() {
                    if (binding.tvTimer.text.equals("00:00")) {
                        binding.tvTimer.text = "STOP"
                    } else {
                        binding.tvTimer.text = "2:00"
                        binding.barTimer.progress = 60 * minute
                    }
                }
            }.start()
        }
    }
}