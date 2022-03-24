package com.fitin.ui.workout

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import com.fitin.databinding.ActivityBlankWorkoutBinding
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiClient
import com.fitin.ui.exercise.CreateTemplateAdapter
import com.fitin.ui.login.LoginActivity
import com.fitin.ui.login.LoginData
import com.google.gson.Gson
import java.util.HashMap


class StartWorkoutActivity : AppCompatActivity(), CreateTemplateView {

    private val binding by lazy {ActivityBlankWorkoutBinding.inflate(layoutInflater) }
    private lateinit var presenter: CreateTemplatePresenter
    private lateinit var pref: PrefManager
    private lateinit var createTemplateAdapter: CreateTemplateAdapter
    private lateinit var data: ArrayList<CreateTemplateModel.CreateTemplateModelItem>
    private var m_Text = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        pref = PrefManager(this)
//        pref.put("template","[]")
        binding.addExercise.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }
        presenter= CreateTemplatePresenter(this,ApiClient.getService(), PrefManager(this))
    }

    override fun onResume() {
        super.onResume()
        createTemplateAdapter = CreateTemplateAdapter(arrayListOf(), object: CreateTemplateAdapter.AdapterListener {
            override fun onClick(exercise: ExerciseModel.Data) {

            }
        })
        binding.exercise.adapter = createTemplateAdapter
        var data = Gson().fromJson(pref.getString("template"), CreateTemplateModel::class.java)
        Log.e("data",data.toString())
        createTemplateAdapter.addList( data )
    }



    override fun setupListener(){
        binding.cancel.setOnClickListener {
            pref.put("template","[]")
            onBackPressed()
        }
        binding.finish.setOnClickListener{
            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_TEXT
            input.hint = "Workout Name"

            if (binding.workoutName.text.toString().equals("")){
                binding.workoutName.setError("Please Fill Workout Name")
            }else{
                AlertDialog.Builder(this@StartWorkoutActivity)
                    .setTitle("Are You Finished")
                    .setMessage("All empty sets will be discarded.\n" +
                            "All sets with valid data will be automatically marked as complete ?")
                    .setPositiveButton("Finish Workout") { dialog, which ->

                        AlertDialog.Builder(this@StartWorkoutActivity)
                            .setTitle("Finish Workout?")
                            .setPositiveButton("Finish") { dialog, which ->
                                val params = HashMap<String?, String?>()
                                var gson = Gson()
                                var data_login = gson.fromJson(pref.getString("user_login"), LoginData::class.java)
                                params["iduser"] = data_login.idusers
                                params["workout_name"] = binding.workoutName.text.toString()
                                data = Gson().fromJson(pref.getString("template"), CreateTemplateModel::class.java)
                                params["template"] = Gson().toJson( data )
                                Log.e("params",params.toString())
                                if(data.isEmpty()){
                                    Toast.makeText(applicationContext, "Please add exercise", Toast.LENGTH_SHORT).show()

                                }else{
                                    presenter.saveWorkoutHistory(
                                        params
                                    )
                                }

                            }
                            .setNegativeButton("Cancel"){dialog, which ->
                                dialog.cancel()
                            }
                            .setCancelable(false).show()

                    }
                    .setNegativeButton("Resume"){dialog, which ->
                        dialog.cancel()
                    }
                    .setCancelable(false).show()
            }

        }



    }
    override fun viewLoading(loading: Boolean){}
    override fun viewResponse(response: WorkoutResponse){}
    override fun historyResponse(response: FinishModel){
        if (response.metadata!!.code==200) {
            Toast.makeText(applicationContext, "Workout Finished", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, FinishWorkoutActivity::class.java)
            intent.putExtra("workout_data",Gson().toJson(response))
            intent.putExtra("workout_name",response.workoutName)
            intent.putExtra("date_workout",response.dateWorkout)
            startActivity(intent)

        } else {
            Toast.makeText(applicationContext, "Internal Server Error", Toast.LENGTH_SHORT).show()
        }


    }
    override fun viewError(msg: String){}
}