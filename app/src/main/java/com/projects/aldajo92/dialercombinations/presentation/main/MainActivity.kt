package com.projects.aldajo92.dialercombinations.presentation.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.projects.aldajo92.dialercombinations.NumericDialListener
import com.projects.aldajo92.dialercombinations.NumericDialerView
import com.projects.aldajo92.dialercombinations.databinding.ActivityMainBinding
import com.projects.aldajo92.dialercombinations.presentation.ViewModelFactory

class MainActivity : AppCompatActivity(), NumericDialListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var dialerView: NumericDialerView

    private val viewModel: MainViewModel by viewModels { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        dialerView = binding.dialerView.apply {
            this.setNumericDialListener(this@MainActivity)
        }

        viewModel.calculationResultLiveData.observe(this, {
            binding.editTextResult.setText(it)
            binding.editTextResult.visibility = View.VISIBLE
        })

        binding.buttonCalculate.setOnClickListener {
            viewModel.calculateCombinations(binding.textViewDialer.text.toString())
        }

        binding.buttonDelete.setOnClickListener {
            binding.textViewDialer.text = ""
            binding.editTextResult.setText("")
            binding.editTextResult.visibility = View.GONE
        }
        setContentView(binding.root)
    }

    override fun onDialNumberListener(number: Int) {
        val newText = binding.textViewDialer.text.toString() + number.toString()
        binding.textViewDialer.text = newText
        Toast.makeText(this, number.toString(), Toast.LENGTH_SHORT).show()
    }
}