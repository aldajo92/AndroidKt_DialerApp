package com.projects.aldajo92.dialercombinations.presentation.main

import androidx.lifecycle.Observer
import com.projects.aldajo92.dialercombinations.CoroutinesTestRule
import com.projects.aldajo92.dialercombinations.domain.CombinationDial
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import androidx.arch.core.executor.testing.InstantTaskExecutorRule

/* Test based on:
* https://stackoverflow.com/questions/49840444/mutablelivedata-is-null-in-junittest
* https://medium.com/androiddevelopers/easy-coroutines-in-android-viewmodelscope-25bffb605471
* https://medium.com/pxhouse/unit-testing-with-mutablelivedata-22b3283a7819
* https://medium.com/@muratcanbur/unit-test-your-livedata-and-viewmodel-3b224f71e981
* */

@ExperimentalCoroutinesApi
class MainViewModelTest : TestWatcher() {

    @MockK
    lateinit var combinationDial: CombinationDial

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mainViewModel = MainViewModel(combinationDial)
    }

    @Test
    fun whenCalculateCombinationIsCalled_thenItReturnsTheResultThroughLiveData() {
        // Given
        val dialNumberText = "123"
        val combinationOutput = listOf("ABC", "ABD")
        val mockedObserver = spyk(Observer<String> {})
        coEvery {
            combinationDial.getAllCombinationList(listOf(1, 2, 3))
        } returns combinationOutput

        val liveData = mainViewModel.calculationResultLiveData

        liveData.observeForever(mockedObserver)

        // When
        mainViewModel.calculateCombinations(dialNumberText)

        //Then
        val slot = slot<String>()
        verify { mockedObserver.onChanged(capture(slot)) }

        assertEquals("ABC \tABD", slot.captured)

    }

}