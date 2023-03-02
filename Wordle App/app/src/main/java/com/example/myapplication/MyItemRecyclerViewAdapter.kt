package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentItemListBinding
import com.example.myapplication.placeholder.PlaceholderContent.PlaceholderItem


/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */

var workIndexs= mutableListOf<Int>()

class AlphaHolder(
    private val binding: FragmentItemListBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item:String, index:Int, onClickOf:(list:List<Int>)->Unit, enabled: () -> Unit,
        enabledFalse: () -> Unit,clearEnabled: () -> Unit,updateIndex:(indexs:List<Int>)->Unit
    ) {
        binding.charS.text = item
        binding.charS.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    if(workIndexs.contains(index)) {
                        workIndexs = workIndexs.subList(0, workIndexs.indexOf(index) + 1)
                        updateIndex(workIndexs);
                    }
                    else if(Integrate.checkAlphaPosition(index, workIndexs.toList())){
                        workIndexs.add(index)
                        updateIndex(workIndexs)
                    }
                    onClickOf(workIndexs.toList())
                    if(Integrate.isValidLength(workIndexs.toList())){enabled()}
                    if(workIndexs.size>0){clearEnabled()}
                    else{enabledFalse() }
                }
            }
            return@setOnTouchListener view.onTouchEvent(motionEvent) ?: true
        }
    }
}


class MyItemRecyclerViewAdapter(
    val context: Context,
    private var values: List<String>,
    private val onClickOf: (list: List<Int>) -> Unit,
    private val enabled: () -> Unit,
    private val enabledFalse: () -> Unit,
    private val clearEnabled: () -> Unit,
    private val updateIndex:(indexs:List<Int>)->Unit
) : RecyclerView.Adapter<AlphaHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphaHolder {

        val inflater=LayoutInflater.from(parent.context)
        val binding=FragmentItemListBinding.inflate(inflater, parent, false)
        //val button:Button=v.findViewById<Button>(R.id.charS)
        return AlphaHolder(binding)

    }

    override fun onBindViewHolder(holder: AlphaHolder, position: Int) {
        val item = values[position]
        holder.bind(item,position,onClickOf,enabled,enabledFalse,clearEnabled,updateIndex)
    }

    fun reset(){
        workIndexs= mutableListOf<Int>()
    }
    override fun getItemCount(): Int = values.size

    fun update(str:ArrayList<String>){
        values = str
        this.notifyDataSetChanged()
    }

    fun resetList(){
        workIndexs= mutableListOf<Int>()
        onClickOf(workIndexs.toList())
    }

}