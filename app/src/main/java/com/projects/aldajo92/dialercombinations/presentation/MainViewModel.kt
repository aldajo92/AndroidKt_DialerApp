package com.projects.aldajo92.dialercombinations.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.aldajo92.dialercombinations.domain.CombinationDial
import com.projects.aldajo92.dialercombinations.domain.CombinationDialImpl
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

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

    private val _calculationResultLiveData = MutableLiveData<String>()
    val calculationResultLiveData: LiveData<String> get() = _calculationResultLiveData


    fun calculateCombinations(valueString: String) {
        viewModelScope.launch {
            val listIntegers = valueString.map {
                it.toString().toInt()
            }
            val result = combinationDial.getAllCombinationList(listIntegers).toString()
            _calculationResultLiveData.postValue(result)
        }
    }

}