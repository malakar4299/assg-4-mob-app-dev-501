package com.example.myapplication

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import com.example.myapplication.databinding.ActivityMainBinding


class Words : Fragment() {
    private lateinit var newGame: Button
    private lateinit var score: TextView

    private var gameScore = "0"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_words, container, false)
        score=view.findViewById(R.id.score)
        score.setTextColor(R.color.green_l)
        newGame= view.findViewById(R.id.new_game)


        newGame.setOnClickListener {
            Integrate.finalScore = 0
            var activity:MainActivity=getActivity() as MainActivity
            activity.re_generate()
        }
        return view
    }
    fun update_scro(){
        Log.i("finalScore", Integrate.finalScore.toString())
        gameScore = Integrate.finalScore.toString()
        Log.i("at Score",gameScore)
        score.text=gameScore
    }
}