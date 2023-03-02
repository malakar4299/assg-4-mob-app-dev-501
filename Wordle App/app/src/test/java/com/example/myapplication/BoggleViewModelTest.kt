package com.example.myapplication

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BoggleViewModelTest{

    private lateinit var boggleViewModel: BoggleViewModel

    @Before
    fun setUp() {
        boggleViewModel = BoggleViewModel()
    }

    @Test
    fun emptyList_test() {
        boggleViewModel.emptyList()
        assertEquals(mutableListOf<Int>(), boggleViewModel.wordIndex)
    }

    @Test
    fun adding_to_wordIndex() {
        var temp=mutableListOf<Int>();
        temp.add(2)
        boggleViewModel.addWordIndex(2)
        assertEquals(temp, boggleViewModel.wordIndex)
    }

    @Test
    fun update_wordIndex(){
        var temp= listOf<Int>(1,2,3)
        boggleViewModel.updateWordIndex(temp)
        assertEquals(temp as MutableList<Int>,boggleViewModel.wordIndex)
    }
}