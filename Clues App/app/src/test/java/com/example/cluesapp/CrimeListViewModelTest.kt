package com.example.cluesapp

import org.junit.Assert.*
import org.junit.Test

class CrimeListViewModelTest{
    @Test
    fun crimeListTestSize(){
        val crimeListViewModel = CrimeListViewModel()
        assertEquals(100, crimeListViewModel.crimes.size)
    }
}