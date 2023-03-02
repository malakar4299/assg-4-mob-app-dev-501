package com.example.myapplication

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlphabetsTest {
    private lateinit var scenario: FragmentScenario<Alphabets>

    @Before
    fun setUp() {
        scenario= launchFragmentInContainer(themeResId = R.style.Theme_MyApplication)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun submitOnClickTester() {
        //Espresso.onView(withId(R.id.nextButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.submit)).perform(ViewActions.click())
        val boggleViewModel = BoggleViewModel()
        //boggleViewModel.wordIndex.size>=4
        assertEquals(mutableListOf<Int>(), boggleViewModel.wordIndex)
    }

    @Test
    fun clearOnClickTester() {
        //Espresso.onView(withId(R.id.nextButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.submit)).perform(ViewActions.click())
        val boggleViewModel = BoggleViewModel()
        assertEquals(mutableListOf<Int>(), boggleViewModel.wordIndex)
    }

    @After
    fun tearDown() {
        scenario.close()
    }
}