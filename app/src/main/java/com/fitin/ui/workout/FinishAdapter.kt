package com.fitin.ui.workout

import com.fitin.databinding.AdapterWorkoutBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fitin.databinding.AdapterFinishBinding


class FinishAdapter (
    var exercises: ArrayList<FinishModel.Data?>,
    var listener: AdapterListener
): RecyclerView.Adapter<FinishAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AdapterFinishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = exercises?.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.binding.exercise.text = exercise?.setCount+" X "+exercise?.exerciseName
        holder.binding.bestSet.text = exercise?.reps+" x "+exercise?.dumbleWeight

    }

    class ViewHolder(val binding: AdapterFinishBinding): RecyclerView.ViewHolder(binding.root)

    fun addList(list: List<FinishModel.Data?>) {
        exercises?.clear()
        exercises?.addAll(list)
        notifyDataSetChanged()
    }



    interface AdapterListener {
        fun onClick(exercises: FinishModel.Data)
    }

}