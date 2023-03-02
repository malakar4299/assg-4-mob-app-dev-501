package com.example.cluesapp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CrimeListFragmentTest {
    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testFragmentIsDisplayed() {
        val crimeListViewModel = CrimeListViewModel()
        assertEquals(100, crimeListViewModel.crimes.size)
    }
}