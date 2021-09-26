package com.projects.aldajo92.dialercombinations

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.projects.aldajo92.dialercombinations.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}