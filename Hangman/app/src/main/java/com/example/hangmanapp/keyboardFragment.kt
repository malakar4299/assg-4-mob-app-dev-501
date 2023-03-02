package com.example.hangmanapp

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener

import androidx.lifecycle.get

class keyboardFragment : Fragment() {

    companion object {
        fun newInstance() = keyboardFragment()
        val buttons = arrayOfNulls<Button>(32)
        private const val Buttons_Key = "ABC"
        private var hintCount = 0
        private const val hintKey = "Hint"
        private var hint: String = "NaN"

    }
    interface OnLetterClickListener {
        fun onLetterClick(letter: String)
    }

    private var listener: OnLetterClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnLetterClickListener

    }

    private lateinit var viewModel: KeyboardViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        retainInstance(true)
        val metrics = resources.displayMetrics
        val screenWidth = metrics.widthPixels

        val cellWidth = screenWidth / 8
        val cellHeight = cellWidth // square

        val view = inflater.inflate(R.layout.fragment_keyboard, container, false)

//        viewModel =  ViewModelProvider.of(this).get()
        viewModel = ViewModelProvider(this).get(KeyboardViewModel::class.java)
        viewModel.buttons = buttons

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val scrollView = view.findViewById<ScrollView>(R.id.scroll)
            val params = scrollView.layoutParams
            params.height = cellWidth
            scrollView.layoutParams = params
        }


//        val animationFragment = fragmentManager.findFragmentById(R.id.animationFragment) as animationFragment

//        parentFragment.begin




        if(savedInstanceState!=null){
            hintCount = savedInstanceState.getInt(hintKey)
        }


        val gridLayout: GridLayout = view.findViewById(R.id.grid)
        gridLayout.columnCount = 8
//        gridLayout.cellH
//        gridLayout.colu

//        val myButton = view.findViewById<Button>(R.id.my_button)
//
//        // Set a click listener on the button
//        myButton.setOnClickListener {
//            // Check if the click event is from the button we're interested in
//            if (it == myButton) {
//                // Get the text of the clicked button
//                val buttonText = myButton.text
//
//                // Log the button text to the console
//                Log.d("MyFragment", "Clicked button text: $buttonText")
//                println("text  $buttonText")
//            }
//        }
        for (i in 0 until gridLayout.childCount) {
            val child = gridLayout.getChildAt(i)
            buttons[i] = gridLayout.getChildAt(i) as Button

            if (buttons[i] is Button) {
                buttons[i]?.setBackgroundColor(Color.rgb(165,42,42))
                buttons[i]?.setTextColor(Color.YELLOW)



                if(savedInstanceState!=null){
                    var boools  = savedInstanceState.getBooleanArray(Buttons_Key)
                    if(buttons[i]!= null){
                        buttons[i]?.isVisible  = boools?.get(i) ?: true

                    }
                }
                buttons[i]?.setOnClickListener {
                    Toast.makeText(activity, "buttom${buttons[i]?.text}clicked", Toast.LENGTH_SHORT).show()
                    SendMessage(buttons[i]?.text.toString())
                    println("button more   ")
                    buttons[i]?.isEnabled = false
                }
            }
//            println("chilid"child)


//            var model = AnimationViewModel()
//            model.updateAnswer("z")

            if (child is Button) {
                child.setOnClickListener {
//                    Toast.makeText(activity, "button   ${child.text}   clicked", Toast.LENGTH_SHORT).show()
                    SendMessage(buttons[i]?.text.toString())
                    println("button more   ")
//                    buttons[i]?.isEnabled = false
                    buttons[i]?.isVisible = false

                }
            }


//            child.setOnClickListener { this }
//            print("child")
//            child =
//            println(child::class.java)
//            child.text

//            if (child is Button) {
//                val button: Button = child as Button
////                button.setOnClickListener(this)/
////                button.setOnClickListener { view ->
////                    this.clicked()
////                }
//
//                button.setOnClickListener{this}
//            }
            val layoutParams = child.layoutParams as GridLayout.LayoutParams

            layoutParams.width = cellWidth
//            if (i == 26){
//                layoutParams.width = cellWidth*2
//
//                layoutParams.width = cellWidth*2
//            }
            layoutParams.height = cellHeight
//            print(cellHeight)
//            println("-HEi  wid -$cellWidth")
            child.layoutParams = layoutParams
        }


        setFragmentResultListener("Hints") { requestKey, bundle ->

        hint = bundle.getString("Hints").toString()

        }



        setFragmentResultListener("VowelKill") { requestKey, bundle ->
            buttons[0]?.isVisible = false
            buttons[4]?.isVisible = false
            buttons[8]?.isVisible = false
            buttons[14]?.isVisible = false
            buttons[20]?.isVisible = false

        }

        setFragmentResultListener("HalfResult"){requestKey, bundle ->

            var booool = bundle.getBooleanArray("HalfResult")

            for (i in 0 until gridLayout.childCount) {
                buttons[i] = gridLayout.getChildAt(i) as Button
                if (buttons[i] is Button) {
                    if (buttons[i] != null) {
                        buttons[i]?.isVisible = booool?.get(i) ?: true

                    }
                }

            }
        }



        var hintButton = view.findViewById<Button>(R.id.hint)

//        hintButton.layoutParams.width = cellWidth*2

//        val params = hintButton.layoutParams
//        params.width = // 设置宽度为 300 像素
//        hintButton.layoutParams = params

        hintButton.setOnClickListener{
            hint()
        }

        setFragmentResultListener("End") { requestKey, bundle ->

            println("One turn finished Known!")
            for (button in buttons){
                if (button != null) {
                    button.isEnabled = false
                }
            }
            hintButton.isEnabled=true


            hintButton.setOnClickListener {
                reSet()
                setFragmentResult("Restart", bundleOf("Restart" to "good luck"))

            }
        }
//        val child = gridLayout.getChildAt(0)
////            println("chilid"child)
//
//        if (child is Button) {
//            child.setOnClickListener {
////                Toast.makeText(activity, "按钮${child.text}被点击了", Toast.LENGTH_SHORT).show()
//                reSet()
//            }
//        }

        // 返回视图对象
        return view
    }

    private fun hint() {
        println("Try to get hint here")
//        reSet()
//        Toast.makeText(activity,"Do you want a hint?  Times $hintCount",Toast.LENGTH_SHORT).show()

        when(hintCount){
            0 ->     Toast.makeText(activity,"Hint: $hint",Toast.LENGTH_SHORT).show()
            1 ->     setFragmentResult("HintHalf", bundleOf("HintHalf" to getButtonVisi()))
            2 ->     setFragmentResult("HintVowel", bundleOf("HintVowel" to "go"))


            else ->   Toast.makeText(activity, "Watch out not become a corpus", Toast.LENGTH_SHORT).show()




        }
        hintCount++




    }


//    override fun onClick(view: View) {
//        handleButtonClick(view)
//    }



    fun SendMessage(char: String){
        // to do here  connect it between different fragment.  TO-DO
        println("Char $char wanted to be sent")
//        listener?.onLetterClick(char)
        setFragmentResult("requestKey", bundleOf("bundleKey" to char))
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(KeyboardViewModel::class.java)
        // TODO: Use the ViewModel
    }

    public fun reSet(){
        for (button in buttons){
            if (button != null) {
                button.isVisible = true
                button.isEnabled = true
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("Try to save")

        outState.putBooleanArray(Buttons_Key,getButtonVisi())
        outState.putInt(hintKey, hintCount)

        print("button visibility saved ")


    }




    public fun getButtonVisi() :BooleanArray{
        val booleanArray =  BooleanArray(27)

        var ind = 0
        for (but in buttons){
            if (but != null) {
                booleanArray[ind] = but.isVisible
            }
            ind++
        }

        return booleanArray
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val viewModel = ViewModelProvider(this).get(KeyboardViewModel::class.java)
        val button = viewModel.buttons
        println("Try to reload")

    }

}