package com.fitin.ui.exercise

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.fitin.R
import com.fitin.databinding.ActivityExerciseBinding
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiClient
import com.fitin.ui.workout.*
import com.fitin.ui.workout.ExerciseActivity
import com.google.gson.Gson


class ExerciseActivity : AppCompatActivity(), ExerciseView {
    private val binding by lazy { ActivityExerciseBinding.inflate(layoutInflater) }
    private lateinit var presenter: ExercisePresenter
    private lateinit var exerciseAdapter: CreateExerciseAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var pref: PrefManager
    val list: MutableList<String> = ArrayList()
    val list_categories: MutableList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = ExercisePresenter(this, ApiClient.getService(),
            PrefManager(this)
        )
        pref = PrefManager(this)


        exerciseAdapter =CreateExerciseAdapter(arrayListOf(), object: CreateExerciseAdapter.AdapterListener {
            override fun onClick(exercise: ExerciseModel.Data) {
                exercise.type = "1"
                val ex = Gson().toJson( exercise)
                pref.put("detail_exercise",ex)
                val intent = Intent(applicationContext, DetailExerciseActivity::class.java)
                startActivity(intent)


            }
        })
        binding.exercise.adapter = exerciseAdapter
        binding.finish.hide()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.exercise_add_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.create){
            val intent = Intent(applicationContext, CreateExerciseActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setupListener(){


        binding.search.doAfterTextChanged {
            val params = HashMap<String?, String?>()
            params["keyword"] = it.toString()
            presenter.fetchExercise(params)
        }

        categoriesAdapter = CategoriesAdapter(arrayListOf(), object: CategoriesAdapter.AdapterListener {
            override fun onClick(categories: CategoriesModel.Data) {
                if(list_categories.contains(categories.categories)){
                    list_categories.remove(categories.categories)
                }else{
                    list_categories.add(categories.categories.toString())
                }

                val params = HashMap<String?, String?>()
                params["keyword"] = binding.search.text.toString()
                params["categories"] = Gson().toJson( list_categories)
                Log.e("params",params.toString())
                presenter.fetchExercise(params)

            }
        })
        binding.categories.adapter = categoriesAdapter


    }

    override fun onResume() {
        super.onResume()
        val params = HashMap<String?, String?>()
        params["keyword"] = binding.search.text.toString()
        params["categories"] = Gson().toJson( list_categories)
        Log.e("params",params.toString())
        presenter.fetchExercise(params)
    }
    override fun viewLoading(loading: Boolean){

    }
    override fun exerciseResponse(response: ExerciseModel){
        exerciseAdapter.addList( response.data )
    }
    override fun categoriesResponse(response: CategoriesModel){
        categoriesAdapter.addList( response.data )
    }
    override fun viewError(msg: String){

    }
    override fun onBackPressed() {
        //this is only needed if you have specific things
        //that you want to do when the user presses the back button.
        /* your specific things...*/
        super.onBackPressed()
    }
}