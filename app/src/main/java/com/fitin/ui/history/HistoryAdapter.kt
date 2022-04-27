package com.fitin.ui.history

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.fitin.R
import com.fitin.databinding.AdapterHistoryBinding
import com.fitin.preferences.PrefManager
import com.fitin.ui.workout.CreateTemplateModel
import com.fitin.ui.workout.SetAdapter
import com.fitin.ui.workout.StartWorkoutActivity
import com.fitin.ui.workout.deleteResponse
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import java.util.HashMap


class HistoryAdapter (
    var histories: ArrayList<HistoryModel.Data?>,
    var listener: AdapterListener
): RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){
    private lateinit var context: Context
    val list: MutableList<String> = ArrayList()
    private lateinit var pref: PrefManager

    private lateinit var detailAdapter: HistoryDetailAdapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.getContext()
        pref = PrefManager(context)
        return ViewHolder(
            AdapterHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun getItemCount() = histories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = histories[position]
        holder.binding.month.text = history?.dateInput
        holder.binding.workoutCount.text = history?.workoutCount+" Workouts"
        detailAdapter = HistoryDetailAdapter(arrayListOf(), object: HistoryDetailAdapter.AdapterListener {
            override fun onClick(delete: deleteResponse) {
                listener.onClick(delete)
            }
        })
        holder.binding.workout.adapter = detailAdapter
        history?.workout?.let { detailAdapter.addList(it) }


    }

    class ViewHolder(val binding: AdapterHistoryBinding): RecyclerView.ViewHolder(binding.root)

    fun addList(list: List<HistoryModel.Data?>) {
        histories.clear()
        histories.addAll(list)
        notifyDataSetChanged()
    }
    fun removeList(postion:Int) {
        histories.removeAt(postion)
        notifyDataSetChanged()
    }


    interface AdapterListener {
        fun onClick(histories: deleteResponse)
    }

}