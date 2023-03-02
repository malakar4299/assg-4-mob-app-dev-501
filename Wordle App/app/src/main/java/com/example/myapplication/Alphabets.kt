package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.network.DictionaryAPIService
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class Alphabets : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var submit: Button
    private lateinit var clear: Button
    private lateinit var charGrids: MyItemRecyclerViewAdapter
    private lateinit var boggleViewModel: BoggleViewModel
    private lateinit var card: CardView
    private lateinit var textView: TextView
    val activity: Activity = Activity()
    lateinit var alpha:ArrayList<String>

    var wordList= mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //gridView= getView()?.findViewById(R.id.grid) ?:
        val view=inflater.inflate(R.layout.fragment_alphabets, container, false )
        textView=view.findViewById(R.id.word)
        val bundle = arguments
        if(bundle != null){
            alpha = bundle.getStringArrayList("alpha") as ArrayList<String>
        }else{
            alpha = ArrayList()
        }

        var wordIndex=bundle?.getIntegerArrayList("wordIndex")
        if (wordIndex == null) {wordIndex= ArrayList<Int>() }
        boggleViewModel= ViewModelProvider(this).get(BoggleViewModel::class.java)
        submit= view.findViewById(R.id.submit)
        clear=view.findViewById(R.id.clear)
        card=view.findViewById<CardView>(R.id.cardV)
        if (alpha.isNotEmpty()){
            recyclerView= view.findViewById(R.id.list)
            card.visibility=View.VISIBLE
            charGrids=  MyItemRecyclerViewAdapter(activity, alpha.toList(), { selected ->
                val handler = Handler()
                handler.postDelayed(Runnable {
                    for (frag in 0..alpha.size) {
                        val list_view = recyclerView.getLayoutManager()?.findViewByPosition(frag)
                        var button: Button? = list_view?.findViewById(R.id.charS) as Button?
                        //set the color to black
                        if (selected.contains(frag)) {
                            if (button != null) {
                                button.elevation = 100F
                            };
                        } else {
                            if (button != null) {
                                button.elevation = 5F
                            };
                        }
                    }
                }, 200);
            },{->submit.isEnabled=true},{ -> submit.isEnabled = false }, {->clear.isEnabled=true},
                {wordIndex:List<Int>->
                    boggleViewModel.updateWordIndex(wordIndex)
                    updateText(alpha)
                })
            recyclerView.layoutManager = GridLayoutManager(activity, 4)
            recyclerView.hasOnClickListeners()
            recyclerView.adapter=charGrids
        }

        clear.setOnClickListener {
            restartFragment()
            if (alpha != null) {updateText(alpha)}
        }
        submit.setOnClickListener {
            Log.i("Submit", "Submit is being called")
            var finalWord = view.findViewById<TextView>(R.id.word).text.toString()
            boggleViewModel.viewModelScope.launch {
                try {
                    if(wordList.contains(finalWord)){
                        val snack = Snackbar.make(it, "Same word? Come on!!!", Snackbar.LENGTH_LONG)
                        snack.show()
                    }else {
                        Log.i("word being sent", finalWord)
                        val listResult =
                            DictionaryAPIService.DictionaryAPI.retrofitService.getWord(finalWord)
                        Log.i("result log", listResult.toString())
                        Integrate.word(finalWord)
                        val snack = Snackbar.make(it, "Great Word!!!", Snackbar.LENGTH_LONG)
                        snack.show()
                        callingScore()
                        wordList.add(finalWord)
                    }
                }
                catch (e:Exception){
                    val snack = Snackbar.make(it,"Incorrect Word, you get negatives",Snackbar.LENGTH_LONG)
                    snack.show()
                    Log.e("Error generated", e.toString())
                    Integrate.finalScore-=10
                    Log.i("finalscore", Integrate.finalScore.toString())
                    callingScore()
                }
            }
            restartFragment()
            if (alpha != null) {updateText(alpha)}

        }

        return view
    }

    fun restartFragment() {
        boggleViewModel.emptyList()
        submit.isEnabled=false
        clear.isEnabled=false
        charGrids.resetList()
    }

    fun generate(al:List<String>){
        wordList = mutableListOf<String>()
        alpha= ArrayList(al)
        charGrids.update(alpha)
        updateTextToEmpty()
        restartFragment()
    }

    fun callingScore(){
        var activity:MainActivity=getActivity() as MainActivity
        activity.getScore()
    }

    fun updateText(alpha:List<String>){
        val iterate=boggleViewModel.wordIndex.iterator()
        var str=String()
        while (iterate.hasNext()) {
            str=str+alpha.get(iterate.next())
        }
        textView.text=str.toString()

    }

    fun updateTextToEmpty(){
        textView.text= String().toString()
    }
}