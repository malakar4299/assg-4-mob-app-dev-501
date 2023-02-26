package com.example.hangmanapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.res.Resources
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.coroutines.coroutineContext

class AnimationViewModel() : ViewModel() {

    private lateinit var answerArray: Array<String>
    // final answer for current round
    private var answer = ""
    // display of secret word
    private var display = ""
    // record correct letters that player has chosen
    private var correctLetters = mutableListOf<String>()
    // iamge index
    private var image_idx : Int = 0

    val getImageIdx : Int
        get() = image_idx

    fun updateAnswer(answerArray : Array<String>): String {

        val idx = Random().nextInt(answerArray.size)
        answer = answerArray[idx]

        return answer
    }

    /**
     * supposed to get the letter from keyboard fragment
     */
    fun inputLetter(input : String) : Boolean{
        var letter = input

        // if player choose a correct letter, put it into 'correctLetter'
        // and refresh the display
        if(letter in answer.lowercase()){
            correctLetters.add(letter)
            updateDisplay()

        }
        else{
            // wrong guess

            // Player fails
            image_idx++

            return false
        }

        // win this round
        if(checkIfWin()){
            updateAnswer()
            updateDisplay()
            return true
        }

    }




    /**
     * update new display of the secret word
     */
    fun updateDisplay() {
        answer.forEach {
                s -> display += (checkCorrection(s.toString()))
        }
    }

    /**
     * reveal all correctly guessed letter,
     * cover the other letters
     */
    fun checkCorrection(s: String) : String {
        return when (correctLetters.contains(s.lowercase())) {
            true -> s
            false -> "_"
        }
    }

    /**
     * Check if player win this round
     */
    fun checkIfWin() : Boolean {
        answer.lowercase().forEach { a ->
            if(!correctLetters.contains(a.toString())){
                return false
            }
        }
        return true
    }
}