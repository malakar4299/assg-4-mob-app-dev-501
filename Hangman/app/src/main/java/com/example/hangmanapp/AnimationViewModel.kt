package com.example.hangmanapp

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel

class AnimationViewModel() : ViewModel() {

    // final answer for current round
    private var answer = ""
    // display of secret word
    private var display = ""
    // record correct letters that player has chosen
    private var correctLetters = mutableListOf<String>()
    // image index
    private var image_idx : Int = 0
    // count of round
    private var roundNum : Int = 0

    val getImageIdx : Int
        get() = image_idx


    val getDisplay : String
        get() = display

    val getAnswer : String
        get() = answer

    val getRoundNum : Int
        get() = roundNum

    public fun setAnswer(ansStr : String): String {
        correctLetters.clear()
        image_idx = 0
//        updateAnswer(answerArray)
        updateDisplay()

        roundNum += 1

//        answer = answerArray[idx]
        answer = ansStr
//        send
        println("Received data")

        return answer
    }

    public fun increases(){
        image_idx++
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

    fun vaildateNotLetter(input: Char) : Boolean{
        var letter : String = input.lowercase()

        return letter !in answer.lowercase()

    }

    /**
     * Once the round is finished, no matter player wins or failed
     * update to new question answer and its display
     */
    fun roundInit(answerArray : Array<String>) {
        image_idx = 0
//        updateAnswer(answerArray)
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

    fun killVowel() {
        correctLetters.add('a'.toString())
        correctLetters.add('e'.toString())
        correctLetters.add('i'.toString())
        correctLetters.add('o'.toString())
        correctLetters.add('u'.toString())
        updateDisplay()

    }
}