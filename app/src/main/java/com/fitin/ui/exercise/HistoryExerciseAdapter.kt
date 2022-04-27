package com.fitin.ui.exercise

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fitin.databinding.AdapterHistoryBinding
import com.fitin.databinding.AdapterHistoryExerciseBinding
import com.fitin.preferences.PrefManager
import com.fitin.ui.history.HistoryDetailAdapter
import com.fitin.ui.history.HistoryModel


class HistoryExerciseAdapter (
    var histories: ArrayList<HistoryExerciseModel.Data?>,
    var listener: AdapterListener
): RecyclerView.Adapter<HistoryExerciseAdapter.ViewHolder>(){
    private lateinit var context: Context
    val list: MutableList<String> = ArrayList()
    private lateinit var pref: PrefManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.getContext()
        pref = PrefManager(context)
        return ViewHolder(
            AdapterHistoryExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun getItemCount() = histories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = histories[position]
        val set:List<HistoryExerciseModel.Data.Set?>?
        set = histories[position]?.set
        var text:String = ""

        set?.forEach {
            text+=it!!.dumbleWeight.toString()+" Kg x "+it!!.reps+"\n"
        }
        holder.binding.set.text = text
        holder.binding.date.text = history?.dateInput
        holder.binding.workoutname.text = history?.workoutName
        holder.binding.weight.visibility = View.GONE
        holder.binding.estimated.visibility = View.GONE




    }

    class ViewHolder(val binding: AdapterHistoryExerciseBinding): RecyclerView.ViewHolder(binding.root)

    fun addList(list: List<HistoryExerciseModel.Data?>) {
        histories.clear()
        histories.addAll(list)
        notifyDataSetChanged()
    }
    fun removeList(postion:Int) {
        histories.removeAt(postion)
        notifyDataSetChanged()
    }


    interface AdapterListener {
        fun onClick(histories: HistoryExerciseModel.Data)
    }

}