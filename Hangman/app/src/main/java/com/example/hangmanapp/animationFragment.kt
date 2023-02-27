package com.example.hangmanapp

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.nfc.Tag
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.hangmanapp.databinding.FragmentAnimationBinding
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener


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

        val answerArray : Array<String> = resources.getStringArray(R.array.answerArray)

        // initialize fragment
        updateHangmanImage(animationViewModel.getImageIdx, hangman_image)

        animationViewModel.updateAnswer(answerArray)
        animationViewModel.updateDisplay()
        secret_word_view.text = animationViewModel.getDisplay

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            val playerInput : String = bundle.getString("bundleKey").toString()

            val guessResult : Boolean = animationViewModel.newTry(playerInput)


            Log.i(TAG, "[Answer] : "+animationViewModel.getAnswer)

            if(guessResult){
                animationViewModel.updateDisplay()
                secret_word_view.text = animationViewModel.getDisplay

                if (animationViewModel.checkIfWin()){
                    Toast.makeText(activity, "Great Guess!", Toast.LENGTH_SHORT).show()

                    animationViewModel.roundInit(answerArray)
                    updateHangmanImage(animationViewModel.getImageIdx, hangman_image)
                    secret_word_view.text = animationViewModel.getDisplay

                    /**
                     * round ended
                     * need to inform keyboardFragment to reset all button
                     */
                }
            }
            else{

                updateHangmanImage(animationViewModel.getImageIdx, hangman_image)
                Log.i(TAG, "!!!! idx: "+animationViewModel.getImageIdx)
                if (animationViewModel.getImageIdx == 10){
                    Toast.makeText(activity, "You Failed!", Toast.LENGTH_SHORT).show()

                    animationViewModel.roundInit(answerArray)
                    updateHangmanImage(animationViewModel.getImageIdx, hangman_image)
                    secret_word_view.text = animationViewModel.getDisplay
                    /**
                     * round ended
                     * need to inform keyboardFragment to reset all button
                     */
                }
            }


        }
//        val view = inflater.inflate(R.layout.fragment_animation, container, false)
        var image : ImageView = view.findViewById<ImageView>(R.id.hangman_image)
        image.setOnClickListener {
            Toast.makeText(activity, "Animation fragment clicked", Toast.LENGTH_SHORT).show()
            val bundle = Bundle()
            bundle.putString("message", "Hello from KeyboardFragment")

        }

        return view
    }



    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(KeyboardViewModel::class.java)
        // TODO: Use the ViewModel
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

}