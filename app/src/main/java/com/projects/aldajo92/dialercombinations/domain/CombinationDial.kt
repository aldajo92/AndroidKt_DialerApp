package com.projects.aldajo92.dialercombinations.domain

interface CombinationDial {

    suspend fun getAllCombinationList(listNumbers: List<Int>): List<String>

}