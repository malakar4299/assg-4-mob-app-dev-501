package com.example.hangmanapp

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
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
        var view = inflater.inflate(R.layout.fragment_animation, container, false)
        animationViewModel = ViewModelProvider(this).get(AnimationViewModel::class.java)
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

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            val result = bundle.getString("bundleKey")
            // Do something with the result
            Toast.makeText(activity, result, Toast.LENGTH_SHORT).show()

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

//        setFragmentResultListener("requestKey") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
//            val result = bundle.getString("bundleKey")
            // Do something with the result
//        }



//        return view

//    @Deprecated("Deprecated in Java")
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(KeyboardViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun guess() {

    }

}