package com.fitin.ui.workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fitin.databinding.ActivityAddSetBinding
import com.fitin.preferences.PrefManager
import com.google.gson.Gson
import java.sql.Types.NULL

class AddSetActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddSetBinding.inflate(layoutInflater) }
    private lateinit var pref: PrefManager
    private lateinit var data:ArrayList<CreateTemplateModel.CreateTemplateModelItem>
    val list_exercise: HashMap<String,CreateTemplateModel.CreateTemplateModelItem> = HashMap()
    val list_set: HashMap<String,String> = HashMap()
    val list_all_set: HashMap<String,CreateTemplateModel.CreateTemplateModelItem.Set?> = HashMap()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val exercise:String = intent.getStringExtra("exercise").toString()
        pref = PrefManager(this)
        val data_exercise = Gson().fromJson(exercise, CreateTemplateModel.CreateTemplateModelItem::class.java)
        data = Gson().fromJson(pref.getString("template"), CreateTemplateModel::class.java)
        data.forEach {
            list_exercise.put(it.idexercise.toString(),it)
            if(it.set!=null && it.idexercise.toString().equals(data_exercise.idexercise)){
                var no:Int = 0
                it.set.forEach {
                    list_all_set.put(no.toString(),it)
                    no+=1
                }
            }

        }

        binding.finish.setOnClickListener{
            val reps = binding.reps.text.toString()
            val weight = binding.weight.text.toString()
            list_set.put("reps",reps)
            list_set.put("weight",weight)
            list_set.put("idexercise",data_exercise.idexercise.toString())
            val set = Gson().fromJson(list_set.toString(), CreateTemplateModel.CreateTemplateModelItem.Set::class.java)
            val no = list_all_set.size+1
            list_all_set.put(no.toString(),set)
            val ex:ArrayList<CreateTemplateModel.CreateTemplateModelItem.Set?> = ArrayList()
            for ((key, value) in list_all_set) {
                ex.add(value)
            }
            val all_exercise:ArrayList<CreateTemplateModel.CreateTemplateModelItem> = ArrayList()
            for ((key, value) in list_exercise) {
                if (key.equals(data_exercise.idexercise)){
                    value.set = ex
                }
                all_exercise.add(value)
            }
            pref.put("template", Gson().toJson( all_exercise ))
            onBackPressed()
        }
    }
    override fun onBackPressed() {
        //this is only needed if you have specific things
        //that you want to do when the user presses the back button.
        /* your specific things...*/
        super.onBackPressed()
    }
}