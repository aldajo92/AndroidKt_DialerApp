package com.projects.aldajo92.dialercombinations.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.aldajo92.dialercombinations.domain.CombinationDialImpl

class ViewModelFactory :
    ViewModelProvider.NewInstanceFactory() {

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

    private val combinationDial =  CombinationDialImpl(mapValues)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(combinationDial) as T
        }
        throw IllegalArgumentException("Unknown view model")
    }
}