package com.projects.aldajo92.dialercombinations

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.projects.aldajo92.dialercombinations.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NumericDialListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var dialerView: NumericDialerView

    // TODO: Temporal solution
    private val mapValues = listOf(
        "",
        "",
        "ABC",
        "DEF",
        "GHI",
        "JKL",
        "MNO",
        "PRS",
        "TUV",
        "WXY"
    ).mapIndexed { index, data ->
        index to data
    }.toMap()

    private val combinationDial: CombinationDial = CombinationDialImpl(mapValues)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        dialerView = binding.dialerView.apply {
            this.setNumericDialListener(this@MainActivity)
        }

        binding.buttonCalculate.setOnClickListener {
            val listIntegers = binding.textViewDialer.text.map {
                it.toString().toInt()
            }
            val result = combinationDial.getAllCombinationList(listIntegers)
            binding.editTextResult.setText(result.toString())
            binding.editTextResult.visibility = View.VISIBLE
        }

        binding.buttonDelete.setOnClickListener {
            binding.textViewDialer.text = ""
            binding.editTextResult.setText("")
        }
        setContentView(binding.root)
    }

    override fun onDialNumberListener(number: Int) {
        val newText = binding.textViewDialer.text.toString() + number.toString()
        binding.textViewDialer.text = newText
        Toast.makeText(this, number.toString(), Toast.LENGTH_SHORT).show()
    }
}