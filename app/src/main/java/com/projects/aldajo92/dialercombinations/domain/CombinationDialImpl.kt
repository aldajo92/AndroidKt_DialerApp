package com.projects.aldajo92.dialercombinations.domain

class CombinationDialImpl(private val hashMap: Map<Int, String>) : CombinationDial {

    override fun getAllCombinationList(listNumbers: List<Int>) =
        if (listNumbers.isEmpty()) emptyList() else
            listNumbers.mapNotNull {
                hashMap[it]?.stringToListString()
            }.reduceRight { list, total ->
                combineListStringToListString(list, total)
            }

    fun combineStringToListString(input: String, list: List<String>) =
        if (input.isBlank()) list else list.map {
            "$input$it"
        }

    fun combineListStringToListString(input: List<String>, list: List<String>) =
        when {
            input.isEmpty() -> list
            list.isEmpty() -> input
            else -> input.flatMap {
                combineStringToListString(it, list)
            }
        }
}

fun String.stringToListString() = this.map {
    it.toString()
}