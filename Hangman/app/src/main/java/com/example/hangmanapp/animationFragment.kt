package com.example.hangmanapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.hangmanapp.databinding.FragmentAnimationBinding

class animationFragment : Fragment() {
    private lateinit var binding: FragmentAnimationBinding
    companion object {
        fun newInstance() = animationFragment()
    }

    private val animationViewModel: AnimationViewModel = ViewModelProvider(requireActivity()).get(AnimationViewModel::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_animation, container, false)
        var secret_word_view : TextView = view.findViewById<TextView>(R.id.secret_word_view)

        var hangman_image : ImageView = view.findViewById(R.id.hangman_image)

        val image_idx = animationViewModel.getImageIdx

        when (image_idx) {
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

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        animationViewModel = ViewModelProvider(this).get(AnimationViewModel::class.java)

    }

    fun guess() {

    }

}