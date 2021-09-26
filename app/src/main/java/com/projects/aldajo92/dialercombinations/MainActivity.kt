package com.projects.aldajo92.dialercombinations

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.projects.aldajo92.dialercombinations.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NumericDialListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var dialerView: NumericDialerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        dialerView = binding.dialerView.apply {
            this.setNumericDialListener(this@MainActivity)
        }
        setContentView(binding.root)
    }

    override fun onDialNumberListener(number: Int) {
        Toast.makeText(this, number.toString(), Toast.LENGTH_SHORT).show()
    }
}