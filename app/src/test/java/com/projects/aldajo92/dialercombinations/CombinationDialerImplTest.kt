package com.projects.aldajo92.dialercombinations

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CombinationDialerImplTest {

    private lateinit var permutation: CombinationDialImpl

    @Before
    fun setup() {
        val listDial = listOf("", "", "ABC", "DEF", "GHI", "JKL", "MNO", "PRS", "TUV", "WXY")

        val mapValues = listDial.mapIndexed{ index, data ->
            index to data
        }.toMap()

        permutation = CombinationDialImpl(mapValues)
    }

    @Test
    fun testGetAllCombinationList_InputSize1_NumbersDifferentToOneAndZero() {
        val input = listOf(2)
        val output = listOf(
            "A", "B", "C"
        )
        val result = permutation.getAllCombinationList(input)

        output.forEach {
            assertTrue(result.contains(it))
        }
    }

    @Test
    fun testGetAllCombinationList_InputSize2_NumbersDifferentToOneAndZero() {
        val input = listOf(3,4)
        val output = listOf(
            "DG", "DH", "DI",
            "EG", "EH", "EI",
            "FG", "FH", "FI"
        )
        val result = permutation.getAllCombinationList(input)

        output.forEach {
            assertTrue(result.contains(it))
        }
    }

    @Test
    fun testGetAllCombinationList_InputSize3_NumbersDifferentToOneAndZero() {
        val input = listOf(2,3,4)
        val output = listOf(
            "ADG", "ADH", "ADI",
            "AEG", "AEH", "AEI",
            "AFG", "AFH", "AFI",
            "BDG", "BDH", "BDI",
            "BEG", "BEH", "BEI",
            "BFG", "BFH", "BFI",
            "CDG", "CDH", "CDI",
            "CEG", "CEH", "CEI",
            "CFG", "CFH", "CFI"
        )
        val result = permutation.getAllCombinationList(input)

        output.forEach {
            assertTrue(result.contains(it))
        }
    }

    @Test
    fun when_inputIsEmpty_then_testGetAllCombinationList_returnsEmptyList() {
        val input = emptyList<Int>()
        val result = permutation.getAllCombinationList(input)

        assertEquals(emptyList<String>(), result)
    }

    @Test
    fun whenCombinationIsOnes_then_itReturnsEmptyList() {
        val input = listOf(1,1,1)
        val result = permutation.getAllCombinationList(input)

        assertEquals(emptyList<String>(), result)
    }

    @Test
    fun tesCombinationStringToListString(){
        val input = "A"
        val inputList = listOf("BC", "BD")
        val result = permutation.combineStringToListString(input, inputList)
        assertEquals(listOf("ABC", "ABD"), result)
    }

    @Test
    fun whenInputIsEmpty_then_combinationStringToListStringReturnsTheInputList(){
        val input = ""
        val inputList = listOf("BC", "BD")
        val result = permutation.combineStringToListString(input, inputList)
        assertEquals(listOf("BC", "BD"), result)
    }

    @Test
    fun tesCombinationListStringToListString(){
        val input = listOf("A", "B")
        val inputList = listOf("CD", "CF")
        val result = permutation.combineListStringToListString(input, inputList)
        assertEquals(listOf("ACD", "ACF", "BCD", "BCF"), result)
    }

    @Test
    fun whenInputStringIsEmpty_thenListStringToListStringIsTheSameInputListString(){
        val input = emptyList<String>()
        val inputList = listOf("CD", "CF")
        val result = permutation.combineListStringToListString(input, inputList)
        assertEquals(listOf("CD", "CF"), result)
    }

    @Test
    fun whenInputListStringIsEmpty_thenListStringToListStringIsTheSameInput(){
        val input = listOf("A", "B")
        val inputList = emptyList<String>()
        val result = permutation.combineListStringToListString(input, inputList)
        assertEquals(listOf("A", "B"), result)
    }

    @Test
    fun testStringToListString(){
        val input = "ABC"
        val result = input.stringToListString()
        assertEquals(listOf("A", "B", "C"), result)
    }

    @Test
    fun whenStringIsEmpty_thenStringToListStringIsEmpty(){
        val input = ""
        val result = input.stringToListString()
        assertEquals(emptyList<String>(), result)
    }

}
