package com.example.hangmanapp

import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController

@RunWith(RobolectricTestRunner::class)
class AnimationViewModelTest {

    private lateinit var activityController: ActivityController<MainActivity>
    private lateinit var activity: MainActivity
    private lateinit var animationViewModel: AnimationViewModel

    @Before
    fun setUp() {
//        activityController = Robolectric.buildActivity(MainActivity::class.java)
//        println(activityController)
//        activity = activityController.get()
//        println(activity)
//        activityController.create().start().resume()
        animationViewModel = AnimationViewModel()
    }


    @Test
    fun setAnswer() {
        assertEquals("Apple", animationViewModel.setAnswer("Apple"))
    }

    @Test
    fun `test checkIfWin returns true`() {

        animationViewModel.correctLetters = listOf<String>("A","P","P") as MutableList<String>
        animationViewModel.setAnswer("HELLO")
        val result = animationViewModel.checkIfWin()
        assertEquals(true, result)
    }

    @Test
    fun `test checkIfWin returns false`() {
        animationViewModel.correctLetters = listOf<String>("A","P","P") as MutableList<String>
        animationViewModel.setAnswer("HELLO")
        val result = animationViewModel.checkIfWin()
        assertEquals(false, result)
    }
}