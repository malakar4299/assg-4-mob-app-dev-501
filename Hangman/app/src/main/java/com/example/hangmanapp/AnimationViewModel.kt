package com.example.hangmanapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues.TAG
import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.coroutines.coroutineContext

class AnimationViewModel() : ViewModel() {

    // final answer for current round
    private var answer = ""
    // display of secret word
    private var display = ""
    // record correct letters that player has chosen
    private var correctLetters = mutableListOf<String>()
    // image index
    private var image_idx : Int = 0

    val getImageIdx : Int
        get() = image_idx

    val getDisplay : String
        get() = display

    val getAnswer : String
        get() = answer

    fun updateAnswer(answerArray : Array<String>): String {

        val idx = Random().nextInt(answerArray.size)
        answer = answerArray[idx]

        return answer
    }

    /**
     * update new display of the secret word
     */
    fun updateDisplay() {
        // reset display
        display = ""
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
     * supposed to get the letter from keyboard fragment
     */
    fun newTry(input : String) : Boolean{
        var letter : String = input.lowercase()

        // if player choose a correct letter, put it into 'correctLetter'
        // and refresh the display
        if(letter in answer.lowercase()){
            Log.i(TAG, "Right letter!!")
            correctLetters.add(letter)
            updateDisplay()
            return true
        }

        // wrong guess
        image_idx++
        return false

    }

    /**
     * Once the round is finished, no matter player wins or failed
     * update to new question answer and its display
     */
    fun roundInit(answerArray : Array<String>) {
        image_idx = 0
        updateAnswer(answerArray)
        updateDisplay()
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