package com.fitin.ui.workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.fitin.databinding.ActivityFinishWorkoutBinding
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiClient
import com.fitin.ui.exercise.CreateTemplateAdapter
import com.fitin.ui.login.LoginData
import com.google.gson.Gson
import java.util.ArrayList
import java.util.HashMap

class FinishWorkoutActivity : AppCompatActivity(), CreateTemplateView {
    private val binding by lazy { ActivityFinishWorkoutBinding.inflate(layoutInflater) }
    private lateinit var presenter: CreateTemplatePresenter
    private lateinit var data: FinishModel
    private lateinit var pref: PrefManager
    private lateinit var finishAdapter: FinishAdapter
    private lateinit var data_template: ArrayList<CreateTemplateModel.CreateTemplateModelItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = CreateTemplatePresenter(this,ApiClient.getService(), PrefManager(this))
        val workout_data:String = intent.getStringExtra("workout_data").toString()
        Log.e("workout",workout_data);
        data = Gson().fromJson(workout_data, FinishModel::class.java)
        pref = PrefManager(this)
        finishAdapter = FinishAdapter(arrayListOf(), object: FinishAdapter.AdapterListener {
            override fun onClick(exercise: FinishModel.Data) {

            }
        })
        binding.workoutname.text = intent.getStringExtra("workout_name")
        binding.dateWorkout.text = intent.getStringExtra("date_workout")
        binding.exercise.adapter =finishAdapter
        finishAdapter.addList(data.data)

        AlertDialog.Builder(this@FinishWorkoutActivity)
                        .setTitle("Do you want to save as \n" +
                                "workout template? ")
                        .setMessage("So you can perform it again with easy in the future.")

                        .setPositiveButton("Save As") { dialog, which ->
                            val params = HashMap<String?, String?>()
                            var gson = Gson()
                            var data_login = gson.fromJson(pref.getString("user_login"), LoginData::class.java)
                            params["iduser"] = data_login.idusers
                            params["workout_name"] = intent.getStringExtra("workout_name")
                            data_template = Gson().fromJson(pref.getString("template"), CreateTemplateModel::class.java)
                            params["template"] = Gson().toJson( data_template )
                            presenter.saveWorkout(
                                    params
                            )


                        }
                        .setNegativeButton("Cancel"){dialog, which ->
                            dialog.cancel()
                        }
                        .setCancelable(false).show()




    }

    override fun setupListener(){}
    override fun viewLoading(loading: Boolean){}
    override fun historyResponse(response: FinishModel){}
    override fun viewResponse(response: WorkoutResponse){
        if (response.metadata!!.code==200) {
            Toast.makeText(applicationContext, "Success Save Template", Toast.LENGTH_SHORT).show()
            pref.put("template","[]")
        } else {
            Toast.makeText(applicationContext, "Internal Server Error", Toast.LENGTH_SHORT).show()
        }
    }
    override fun viewError(msg: String){}
}