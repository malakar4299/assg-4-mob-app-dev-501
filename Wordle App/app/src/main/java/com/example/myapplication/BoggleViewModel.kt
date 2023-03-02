package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel

class BoggleViewModel: ViewModel()  {
    var alpha =emptyList<String>();
    var wordIndex= mutableListOf<Int>();
    fun addAlpha(alphaD:List<String>){
        alpha=alphaD
    }

    fun updateWordIndex(word:List<Int>){
        wordIndex= word as MutableList<Int>
    }

    fun addWordIndex(index:Int){
        wordIndex.add(index)
    }

    fun emptyList(){
        wordIndex= mutableListOf<Int>();
    }
}