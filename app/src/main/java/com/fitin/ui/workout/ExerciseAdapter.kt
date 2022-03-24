package com.fitin.ui.exercise

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fitin.R
import com.fitin.databinding.AdapterExerciseBinding
import com.fitin.preferences.PrefManager
import com.fitin.ui.workout.CreateTemplateModel
import com.fitin.ui.workout.ExerciseModel
import com.google.gson.Gson

class ExerciseAdapter (
    var exercises: ArrayList<ExerciseModel.Data>,
    var listener: AdapterListener

): RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {
    private val texts = arrayListOf<TextView>()
    private lateinit var context:Context
    val list: MutableList<String> = ArrayList()
    private lateinit var pref:PrefManager
    private lateinit var data:ArrayList<CreateTemplateModel.CreateTemplateModelItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.getContext()
        pref = PrefManager(context)

        data = Gson().fromJson(pref.getString("template"), CreateTemplateModel::class.java)
        data.forEach {
            var json:String = Gson().toJson(it)
//            if(!list.contains(it.idexercise.toString())){
//                list.add(it.idexercise.toString())
//            }
        }
        return ViewHolder(
            AdapterExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )
    }

    override fun getItemCount() = exercises.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.binding.exercise.text = exercise.exerciseName
        holder.binding.key.text = exercise.exerciseName?.get(0).toString()
        if(list.contains(exercise.idexercise)){
            holder.binding.container.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
        }else{
            holder.binding.container.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
        holder.itemView.setOnClickListener {
//            Log.e("item",exercise.toString())
//            setColor( holder.binding.exercise )
            listener.onClick(exercise )
            if(list.contains(exercise.idexercise)){
                list.remove(exercise.idexercise)
                holder.binding.container.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }else{
                list.add(exercise.idexercise.toString())
                holder.binding.container.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
            }

        }
    }

    class ViewHolder(val binding: AdapterExerciseBinding): RecyclerView.ViewHolder(binding.root)

    fun addList(list: List<ExerciseModel.Data>) {
        exercises.clear()
        exercises.addAll(list)
        notifyDataSetChanged()
    }

    fun addListSelected(idexercise:String) {
        list.add(idexercise)
    }
    fun getSelectedCount():Int {
        return list.size
    }



    interface AdapterListener {
        fun onClick(exercises: ExerciseModel.Data)
    }

}