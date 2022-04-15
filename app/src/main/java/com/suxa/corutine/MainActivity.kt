package com.suxa.corutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ProxyFileDescriptorCallback
import android.widget.Toast
import androidx.core.view.isVisible
import com.suxa.corutine.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonLoad.setOnClickListener {
            loadData()
        }
    }

    private fun loadData() {
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false
        loadCity { city ->
            binding.tvLocation.text = city
            loadTemperature(city) { temp ->
                binding.tvTemperature.text = temp.toString()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
            }
        }
    }

    private fun loadTemperature(city: String, callback: (Int) -> Unit) {
        runOnUiThread {
            Toast.makeText(
                this,
                getString(R.string.loading_temperature_toast, city),
                Toast.LENGTH_SHORT
            ).show()
        }
        Thread.sleep(3000)
        runOnUiThread {
            callback.invoke(30)
        }
    }

    private fun loadCity(callback: (String) -> Unit) {
        thread {
            Thread.sleep(3000)
            runOnUiThread {
                callback.invoke("Tashkent")
            }
        }
    }
}