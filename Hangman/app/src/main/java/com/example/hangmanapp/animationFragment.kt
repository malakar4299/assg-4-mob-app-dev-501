package com.example.hangmanapp

import android.content.ContentValues.TAG
import android.content.res.Configuration
import android.content.res.Resources
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import com.example.hangmanapp.databinding.FragmentAnimationBinding
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import java.util.*


class animationFragment : Fragment() {
    private lateinit var binding: FragmentAnimationBinding
    companion object {
        fun newInstance() = animationFragment()
    }

    private lateinit var animationViewModel: AnimationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_animation, container, false)

        animationViewModel = ViewModelProvider(this).get(AnimationViewModel::class.java)

        val secret_word_view : TextView = view.findViewById(R.id.secret_word_view)

        val hangman_image : ImageView = view.findViewById(R.id.hangman_image)


        // initialize fragment
        updateHangmanImage(animationViewModel.getImageIdx, hangman_image)

        // if this is the first round, initialize the answer in previous
        if(animationViewModel.getRoundNum == 0){
//            animationViewModel.updateAnswer(answerArray)
            prepareGuessWordAndHint()
            animationViewModel.updateDisplay()
        }
        secret_word_view.text = animationViewModel.getDisplay

        // reset scroll view here
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val scrollView = view.findViewById<ScrollView>(R.id.scroll_ani)
            val layoutParams = scrollView.layoutParams as ViewGroup.LayoutParams
            layoutParams.height = 200.dpToPx() // 将dp转换为像素
            scrollView.layoutParams = layoutParams
        }

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            val playerInput : String = bundle.getString("bundleKey").toString()

            val guessResult : Boolean = animationViewModel.newTry(playerInput)


            Log.i(TAG, "[Answer] : "+animationViewModel.getAnswer)

            if(guessResult){
                animationViewModel.updateDisplay()
                secret_word_view.text = animationViewModel.getDisplay
                Toast.makeText(activity, "Nice Hit!", Toast.LENGTH_SHORT).show()


                if (animationViewModel.checkIfWin()){
                    Toast.makeText(activity, "Great Guess! You succeed!!!", Toast.LENGTH_SHORT).show()

//                    animationViewModel.roundInit(answerArray)
                    updateHangmanImage(animationViewModel.getImageIdx, hangman_image)
                    secret_word_view.text = animationViewModel.getDisplay
                    hangman_image.setImageResource(R.drawable.hangwin)


                    roundEnd()

                    /**
                     * round ended
                     * need to inform keyboardFragment to reset all button
                     */
                }
            }
            else{

                Toast.makeText(activity, "Sorry.", Toast.LENGTH_SHORT).show()

                updateHangmanImage(animationViewModel.getImageIdx, hangman_image)
                Log.i(TAG, "!!!! idx: "+animationViewModel.getImageIdx)
                if (animationViewModel.getImageIdx == 10){
                    Toast.makeText(activity, "What a pity! You Failed!", Toast.LENGTH_SHORT).show()

//                    animationViewModel.roundInit(answerArray)
                    updateHangmanImage(animationViewModel.getImageIdx, hangman_image)
                    secret_word_view.text = animationViewModel.getDisplay
                    /**
                     * round ended
                     * need to inform keyboardFragment to reset all button
                     */

                    roundEnd()
                }
            }


        }

        setFragmentResultListener("Restart") { requestKey, bundle ->

            print("anima want to go")
            prepareGuessWordAndHint()


        }

        setFragmentResultListener("HintHalf") { requestKey, bundle ->
            var avalbutton = bundle.getBooleanArray("HintHalf")
            if(animationViewModel.getImageIdx<9){
                avalbutton = updateVisbleArray(avalbutton)
                animationViewModel.increases()
                println("Half before : ${animationViewModel.getImageIdx}")
                updateHangmanImage(animationViewModel.getImageIdx, hangman_image)
                println("Half after : ${animationViewModel.getImageIdx}")
                setFragmentResult("HalfResult", bundleOf("HalfResult" to avalbutton))
            }else{
                Toast.makeText(activity, "You are about to die,take care not become a corpus!", Toast.LENGTH_SHORT).show()
            }

        }

        setFragmentResultListener("HintVowel"){requestKey, bundle ->

            if(animationViewModel.getImageIdx<9){
                println("Half before : ${animationViewModel.getImageIdx}")
                animationViewModel.increases()
                updateHangmanImage(animationViewModel.getImageIdx, hangman_image)
                println("Half after : ${animationViewModel.getImageIdx}")
                animationViewModel.killVowel()
                setFragmentResult("VowelKill", bundleOf("VowelKill" to "genocide"))
                animationViewModel.updateDisplay()
                secret_word_view.text = animationViewModel.getDisplay
            }else{
                Toast.makeText(activity, "You are about to die,take care not become a corpus!", Toast.LENGTH_SHORT).show()
            }

        }


//        val view = inflater.inflate(R.layout.fragment_animation, container, false)
        var image : ImageView = view.findViewById<ImageView>(R.id.hangman_image)
        image.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("message", "Hello from KeyboardFragment")

        }



        return view
    }

    private fun roundEnd() {
        setFragmentResult("End", bundleOf("End" to "replay?"))

    }

    private fun updateVisbleArray(avalbutton: BooleanArray?): BooleanArray? {
        var remain = 0
        if (avalbutton != null) {
            for (char in avalbutton){
                if (char){
                    remain++

                }
            }

            var change = remain/2
            while (change>0){
                 var num = Random().nextInt(26)
                var letter = numberToLetter(num)

                if(avalbutton[num] && animationViewModel.vaildateNotLetter(letter)){
                    avalbutton[num] = false
                    change--
                }

            }

        }

        return avalbutton
    }


    private fun prepareGuessWordAndHint() {
        val answerArray : Array<String> = resources.getStringArray(R.array.answerArray)
        val idx = Random().nextInt(answerArray.size)
        val ranAnsHint = answerArray[idx]

        val ansHint = ranAnsHint.split(" ")



        animationViewModel.setAnswer(ansHint[0])


        setFragmentResult("Hints", bundleOf("Hints" to ansHint[1]))





//        animationViewModel.roundInit(answerArray)



    }


    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(KeyboardViewModel::class.java)
        // TODO: Use the ViewModel
    }


    fun Int.dpToPx(): Int {
        return (this * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun updateHangmanImage(imageIdx : Int, hangman_image : ImageView){
        when (imageIdx) {
            0 -> hangman_image.setImageResource(R.drawable.hangman0)
            1 -> hangman_image.setImageResource(R.drawable.hangman1)
            2 -> hangman_image.setImageResource(R.drawable.hangman2)
            3 -> hangman_image.setImageResource(R.drawable.hangman3)
            4 -> hangman_image.setImageResource(R.drawable.hangman4)
            5 -> hangman_image.setImageResource(R.drawable.hangman5)
            6 -> hangman_image.setImageResource(R.drawable.hangman6)
            7 -> hangman_image.setImageResource(R.drawable.hangman7)
            8 -> hangman_image.setImageResource(R.drawable.hangman8)
            9 -> hangman_image.setImageResource(R.drawable.hangman9)
            10 -> {
                hangman_image.setImageResource(R.drawable.hangman10)
            }
        }
    }

    public fun sendBack(){
        println("Try to send back")
    }

    public fun numberToLetter(number: Int): Char {
        return ('a' + number % 26)
    }

}