package com.fitin.ui.exercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.fitin.R
import com.fitin.databinding.ActivityCreateExerciseBinding
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiClient
import com.fitin.services.ApiService
import com.fitin.ui.login.LoginData
import com.fitin.ui.workout.ExerciseModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.dialog_categories.*
import kotlinx.android.synthetic.main.activity_create_exercise.*

class CreateExerciseActivity : AppCompatActivity(),CreateExerciseView {
    private val binding by lazy { ActivityCreateExerciseBinding.inflate(layoutInflater) }
    var category:String = ""
    var bodypart:String = ""
    private lateinit var presenter: CreateExercisePresenter
    private lateinit var pref: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = CreateExercisePresenter(this,ApiClient.getService(), PrefManager(this))
        pref = PrefManager(this)
        binding.categories.setOnClickListener {
            val inflater = LayoutInflater.from(this)
            val dialogView: View = inflater.inflate(R.layout.dialog_categories, null)
            val categoryRadio:RadioGroup = dialogView.findViewById(R.id.categories)
            if (category.equals("Dumbbell")){
                dialogView.findViewById<RadioButton>(R.id.dumbbell).isChecked = true
            }
            if (category.equals("Barbell")){
                dialogView.findViewById<RadioButton>(R.id.barbell).isChecked = true
            }
            if (category.equals("Machine")){
                dialogView.findViewById<RadioButton>(R.id.machine).isChecked = true
            }
            if (category.equals("Weighted bodyweight")){
                dialogView.findViewById<RadioButton>(R.id.weighted).isChecked = true
            }
            if (category.equals("Assisted body")){
                dialogView.findViewById<RadioButton>(R.id.assisted).isChecked = true
            }
            if (category.equals("Reps only")){
                dialogView.findViewById<RadioButton>(R.id.reps).isChecked = true
            }
            if (category.equals("Ez Bar")){
                dialogView.findViewById<RadioButton>(R.id.ezbar).isChecked = true
            }
            if (category.equals("Medicine Ball")){
                dialogView.findViewById<RadioButton>(R.id.medicine).isChecked = true
            }

            val dialog = AlertDialog.Builder(this)
                .setNegativeButton("Cancel", null)
                .setCancelable(false)
                .setView(dialogView)
                .show()
            categoryRadio.setOnCheckedChangeListener { radioGroup, id ->
                when(categoryRadio.checkedRadioButtonId) {
                    R.id.dumbbell -> {
                        category = "Dumbbell"
                        binding.categoriesSelect.text = category
                        dialog.dismiss()
                    }
                    R.id.barbell -> {
                        category = "Barbell"
                        binding.categoriesSelect.text = category
                        dialog.dismiss()
                    }
                    R.id.machine -> {
                        category = "Machine"
                        binding.categoriesSelect.text = category
                        dialog.dismiss()
                    }
                    R.id.weighted -> {
                        category = "Weighted bodyweight"
                        binding.categoriesSelect.text = category
                        dialog.dismiss()
                    }
                    R.id.assisted -> {
                        category = "Assisted body"
                        binding.categoriesSelect.text = category
                        dialog.dismiss()
                    }
                    R.id.reps -> {
                        category = "Reps only"
                        binding.categoriesSelect.text = category
                        dialog.dismiss()
                    }
                    R.id.ezbar -> {
                        category = "Ez Bar"
                        binding.categoriesSelect.text = category
                        dialog.dismiss()
                    }
                    R.id.medicine -> {
                        category = "Medicine Ball"
                        binding.categoriesSelect.text = category
                        dialog.dismiss()
                    }

                }

            }
        }
        binding.bodypart.setOnClickListener {
            val inflater = LayoutInflater.from(this)
            val dialogView: View = inflater.inflate(R.layout.dialog_bodypart, null)
            val bodypartRadio:RadioGroup = dialogView.findViewById(R.id.bodypart)
            if (bodypart.equals("Core")){
                dialogView.findViewById<RadioButton>(R.id.core).isChecked = true
            }
            if (bodypart.equals("Arms")){
                dialogView.findViewById<RadioButton>(R.id.arms).isChecked = true
            }
            if (bodypart.equals("Back")){
                dialogView.findViewById<RadioButton>(R.id.back).isChecked = true
            }
            if (bodypart.equals("Chest")){
                dialogView.findViewById<RadioButton>(R.id.chest).isChecked = true
            }
            if (bodypart.equals("Legs")){
                dialogView.findViewById<RadioButton>(R.id.legs).isChecked = true
            }
            if (bodypart.equals("Shoulders")){
                dialogView.findViewById<RadioButton>(R.id.shoulders).isChecked = true
            }
            if (bodypart.equals("Others")){
                dialogView.findViewById<RadioButton>(R.id.other).isChecked = true
            }


            val dialog = AlertDialog.Builder(this)
                .setNegativeButton("Cancel", null)
                .setCancelable(false)
                .setView(dialogView)
                .show()
            bodypartRadio.setOnCheckedChangeListener { radioGroup, id ->
                when(bodypartRadio.checkedRadioButtonId) {
                    R.id.core -> {
                        bodypart = "Core"
                        binding.bodypartSelect.text = bodypart
                        dialog.dismiss()
                    }
                    R.id.arms -> {
                        bodypart = "Arms"
                        binding.bodypartSelect.text = bodypart
                        dialog.dismiss()
                    }
                    R.id.back -> {
                        bodypart = "Back"
                        binding.bodypartSelect.text = bodypart
                        dialog.dismiss()
                    }
                    R.id.chest -> {
                        bodypart = "Chest"
                        binding.bodypartSelect.text = bodypart
                        dialog.dismiss()
                    }
                    R.id.legs -> {
                        bodypart = "Legs"
                        binding.bodypartSelect.text = bodypart
                        dialog.dismiss()
                    }
                    R.id.shoulders -> {
                        bodypart = "Shoulders"
                        binding.bodypartSelect.text = bodypart
                        dialog.dismiss()
                    }
                    R.id.other -> {
                        bodypart = "Others"
                        binding.bodypartSelect.text = bodypart
                        dialog.dismiss()
                    }

                }

            }


        }
        binding.finish.setOnClickListener {
            var cat = binding.categoriesSelect.text.toString()
            var body = binding.bodypartSelect.text.toString()
            var exercise_name = binding.exerciseName.text.toString()
            if (exercise_name.length == 0){
                binding.exerciseName.setError("please fill this field")
                Toast.makeText(this,"Please input exercise name",Toast.LENGTH_SHORT).show()
            }else{
                if(cat.equals("None")){

                    Toast.makeText(this,"Please select categories",Toast.LENGTH_SHORT).show()
                }else{
                    if(body.equals("None")){

                        Toast.makeText(this,"Please select body part",Toast.LENGTH_SHORT).show()
                    }else{
                        var gson = Gson()
                        var data = gson.fromJson(pref.getString("user_login"), LoginData::class.java)
                        val params = HashMap<String?, String?>()
                        params["iduser"] = data.idusers
                        params["exercise_name"] = exercise_name
                        params["body_part"] = body
                        params["categories"] = cat
                        params["type"] = "1"
                        presenter.saveExercise(params)
                    }
                }
            }


        }

    }

    override fun setupListener(){}
    override fun viewLoading(loading: Boolean){}
    override fun viewResponse(response: AddResponse){
        if (response.metadata?.code==200){
            Toast.makeText(this,"Success Create Exercise",Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
    }
    override fun viewError(msg: String){}
}