package com.fitin.ui.workout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fitin.databinding.ActivityCreateTemplateBinding
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiClient
import com.fitin.services.ApiService
import com.fitin.ui.exercise.CreateTemplateAdapter
import com.fitin.ui.home.HomeActivity
import com.fitin.ui.login.LoginData
import com.google.gson.Gson
import java.util.*

class CreateTemplateActivity : AppCompatActivity(), CreateTemplateView {
    private val binding by lazy { ActivityCreateTemplateBinding.inflate(layoutInflater) }
    private lateinit var presenter: CreateTemplatePresenter
    private lateinit var pref:PrefManager
    private lateinit var createTemplateAdapter: CreateTemplateAdapter
    private lateinit var data:ArrayList<CreateTemplateModel.CreateTemplateModelItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        pref = PrefManager(this)
        binding.addExercise.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }
        presenter = CreateTemplatePresenter(this,ApiClient.getService(),pref)
    }

    override fun onResume() {
        super.onResume()
        createTemplateAdapter =CreateTemplateAdapter(arrayListOf(), object: CreateTemplateAdapter.AdapterListener {
            override fun onClick(exercise: ExerciseModel.Data) {

            }
        })
        binding.exercise.adapter = createTemplateAdapter
        var data = Gson().fromJson(pref.getString("template"), CreateTemplateModel::class.java)
        Log.e("data",data.toString())
        createTemplateAdapter.addList( data )
    }

    override fun setupListener(){
        binding.saveTemplate.setOnClickListener {
            if(binding.workoutName.text.toString().equals("")){
                binding.workoutName.setError("Please Input Workout Name")
            }else{
                val params = HashMap<String?, String?>()
                var gson = Gson()
                var data_login = gson.fromJson(pref.getString("user_login"), LoginData::class.java)
                params["iduser"] = data_login.idusers
                params["workout_name"] = binding.workoutName.text.toString()
                data = Gson().fromJson(pref.getString("template"), CreateTemplateModel::class.java)
                params["template"] = Gson().toJson( data )
                if(data.isEmpty()){
                    Toast.makeText(applicationContext, "Please add exercise", Toast.LENGTH_SHORT).show()
                }else{
                    presenter.saveWorkout(
                        params
                    )
                }
//                Log.e("params",params.toString())

            }


        }


    }
    override fun viewLoading(loading: Boolean){}
    override fun historyResponse(response: FinishModel){}
    override fun viewResponse(response: WorkoutResponse){
        if (response.metadata!!.code==200) {
            Toast.makeText(applicationContext, "Success Create Template", Toast.LENGTH_SHORT).show()
            pref.put("template","[]")
            onBackPressed()
        } else {
            Toast.makeText(applicationContext, "Internal Server Error", Toast.LENGTH_SHORT).show()
        }


    }
    override fun viewError(msg: String){}
}