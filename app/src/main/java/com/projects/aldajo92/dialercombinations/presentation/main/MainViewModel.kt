package com.projects.aldajo92.dialercombinations.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.aldajo92.dialercombinations.domain.CombinationDial
import kotlinx.coroutines.launch

class MainViewModel(private val combinationDial: CombinationDial) : ViewModel() {

    private val _calculationResultLiveData = MutableLiveData<String>()
    val calculationResultLiveData: LiveData<String> get() = _calculationResultLiveData

    fun calculateCombinations(valueString: String) {
        viewModelScope.launch {
            val listIntegers = valueString.map {
                it.toString().toInt()
            }
            val result = combinationDial.getAllCombinationList(listIntegers).reduce { total, item ->
                "$total \t$item"
            }
            _calculationResultLiveData.postValue(result)
        }
    }

}