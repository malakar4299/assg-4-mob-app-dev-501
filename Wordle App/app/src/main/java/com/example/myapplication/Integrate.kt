package com.example.myapplication

import android.util.Log

class Integrate {
    companion object {

        var finalScore = 0;

        fun checkAlphaPosition(index: Int, indexList: List<Int>): Boolean {
            if(indexList.size==0){ return true}
            val intLast = indexList.get(indexList.size - 1)
            if (intLast + 1 == index) {
                return true
            } else if (intLast - 1 == index) {
                return true
            } else if (intLast + 4 == index) {
                return true
            } else if (intLast - 4 == index) {
                return true
            } else if (intLast + 3 == index) {
                return true
            } else if (intLast - 3 == index) {
                return true
            } else if (intLast + 5 == index) {
                return true
            } else if (intLast - 5 == index) {
                return true
            }
            return false
        }

        fun isValidLength(indexList: List<Int>): Boolean {
            if(indexList.size>=4){return true}
            return false
        }

        fun word(word:String):Boolean{
            Log.i("h",word)
            var score = calculateScore(word)
            finalScore+=score;
            Log.i("score is", score.toString())
            return true
        }

        fun calculateScore(word: String):Int{
            if(!checkWord(word)){
                return -10;
            }
            var score = 0;
            score+= countVowels(word)*10;
            score+=(word.length- countVowels(word))
            if(checkSpecial(word)>=1){
                score*=2;
            }
            return score;
        }

        fun checkWord(word:String):Boolean{
            Log.i("Vowels count", (countVowels(word)>=2).toString())
            return countVowels(word) >= 1
        }

        fun checkSpecial(word:String):Int{
            var vowelsCount = 0
            var testWord = word.lowercase()
            for (i in 0..testWord.length - 1) {
                val ch = testWord[i]
                if (ch == 's' || ch == 'z' || ch == 'p'
                    || ch == 'x' || ch == 'q') {
                    ++vowelsCount
                }
            }
            Log.i("Vowels count", vowelsCount.toString())
            return vowelsCount;
        }


        fun countVowels(word:String):Int{
            var vowelsCount = 0
            var testWord = word.lowercase()
            Log.i("testWord", word)
            for (i in 0..testWord.length - 1) {
                val ch = testWord[i]
                Log.i("Character is", ch.toString())
                if (ch == 'a' || ch == 'e' || ch == 'i'
                    || ch == 'o' || ch == 'u') {
                    ++vowelsCount
                }
            }
            Log.i("Vowels count", vowelsCount.toString())
            return vowelsCount;
        }

    }
}