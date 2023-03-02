package com.example.myapplication

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WordsTest {
    private lateinit var scenario: FragmentScenario<Words>
    @Before
    fun setUp() {
        scenario= launchFragmentInContainer(themeResId = R.style.Theme_MyApplication)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun newGameOnClickTester() {
        //Espresso.onView(withId(R.id.nextButton)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.new_game)).perform(ViewActions.click())
        val boggleViewModel = BoggleViewModel()
        assertEquals(mutableListOf<Int>(), boggleViewModel.wordIndex)
    }

    @After
    fun tearDown() {
        scenario.close()
    }
}