package com.fitin.ui.workout

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fitin.R
import com.fitin.databinding.AdapterExerciseBinding
import com.fitin.databinding.AdapterSetBinding
import com.fitin.preferences.PrefManager
import com.google.gson.Gson

class SetAdapter (
    var sets: ArrayList<CreateTemplateModel.CreateTemplateModelItem.Set?>,
    var listener: AdapterListener

): RecyclerView.Adapter<SetAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var pref: PrefManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.getContext()
        pref = PrefManager(context)


        return ViewHolder(
            AdapterSetBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )
    }

    override fun getItemCount() = sets.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val set = sets[position]
        val no = position+1
        holder.binding.setNumber.text = no.toString()
        holder.binding.reps.text = set?.reps
        holder.binding.weight.text = set?.weight+" Kg"
        holder.binding.removeSet.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Remove Set")
                .setMessage("Are you sure ?")
                .setPositiveButton("Yes") { dialog, which ->
                    removeList(holder.adapterPosition,set?.idexercise.toString())
                    
                }.show()
        }

    }

    class ViewHolder(val binding: AdapterSetBinding): RecyclerView.ViewHolder(binding.root)

    fun addList(list: List<CreateTemplateModel.CreateTemplateModelItem.Set?>) {
        sets.clear()
        sets.addAll(list)
        notifyDataSetChanged()
    }

    fun removeList(postion:Int,idexercise:String) {
        sets.removeAt(postion)
        Log.e("id",idexercise)
        Log.e("set",sets.toString())
        val list_exercise: HashMap<String,CreateTemplateModel.CreateTemplateModelItem> = HashMap()

        val data:ArrayList<CreateTemplateModel.CreateTemplateModelItem>
        data = Gson().fromJson(pref.getString("template"), CreateTemplateModel::class.java)
        data.forEach {
            list_exercise.put(it.idexercise.toString(),it)
        }

        Log.e("data",data.toString())
        val all_exercise:ArrayList<CreateTemplateModel.CreateTemplateModelItem> = ArrayList()
        for ((key, value) in list_exercise) {
            if (key.equals(idexercise)){
                value.set = sets
            }
            all_exercise.add(value)
        }
        Log.e("final",all_exercise.toString())
        pref.put("template", Gson().toJson( all_exercise ))
        list_exercise.clear()
        data.clear()
        all_exercise.clear()
        notifyDataSetChanged()
    }


    interface AdapterListener {
        fun onClick(sets: CreateTemplateModel.CreateTemplateModelItem.Set)
    }

}