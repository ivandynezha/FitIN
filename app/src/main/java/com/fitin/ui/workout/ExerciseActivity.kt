package com.fitin.ui.workout

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.fitin.databinding.ActivityExerciseBinding
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiClient
import com.fitin.ui.exercise.CreateTemplateAdapter
import com.fitin.ui.exercise.ExerciseAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.adapter_workout.*

class ExerciseActivity : AppCompatActivity(), ExerciseView {
    private val binding by lazy { ActivityExerciseBinding.inflate(layoutInflater) }
    private lateinit var presenter: ExercisePresenter
    private lateinit var exerciseAdapter: ExerciseAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var createTemplateAdapter: CreateTemplateAdapter
    private lateinit var pref:PrefManager
    private lateinit var data:ArrayList<CreateTemplateModel.CreateTemplateModelItem>
    val list: MutableList<String> = ArrayList()
    val list_categories: MutableList<String> = ArrayList()
    val list_exercise: HashMap<String?,ExerciseModel.Data> = HashMap()
    val list_set: HashMap<String?,ArrayList<CreateTemplateModel.CreateTemplateModelItem.Set>> = HashMap()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = ExercisePresenter(this, ApiClient.getService(),
            PrefManager(this)
        )
        pref = PrefManager(this)


        exerciseAdapter =ExerciseAdapter(arrayListOf(), object: ExerciseAdapter.AdapterListener {
            override fun onClick(exercise: ExerciseModel.Data) {


                if(list.contains(exercise.idexercise)){
                    list.remove(exercise.idexercise)
                    list_exercise.remove(exercise.idexercise)
                }else{
                    list.add(exercise.idexercise.toString())
                    list_exercise.put(exercise.idexercise,exercise)
                }
                if(list.size<=0){
                    binding.finish.hide()
                    binding.textExercise.text = "Exercise"
                }else{
                    binding.finish.show()
                    binding.textExercise.text = "Exercise ("+list.size+" Item Selected)"
                }
            }
        })
        binding.exercise.adapter = exerciseAdapter

        data = Gson().fromJson(pref.getString("template"), CreateTemplateModel::class.java)
        data.forEach {
            list.add(it.idexercise.toString())
            var json:String = Gson().toJson(it)
            Log.e("id",it.idexercise.toString());
            exerciseAdapter.addListSelected(it.idexercise.toString())
            list_exercise.put(it.idexercise, Gson().fromJson(json, ExerciseModel.Data::class.java))
//            list_set.put(it.idexercise, it.set)

        }
        if(list.size<=0){
            binding.finish.hide()
            binding.textExercise.text = "Exercise"
        }else{
            binding.finish.show()
            binding.textExercise.text = "Exercise ("+list.size+" Item Selected)"
        }

        binding.finish.hide()
    }

    override fun setupListener(){

        binding.finish.setOnClickListener{
            val ex:ArrayList<ExerciseModel.Data> = ArrayList()
            for ((key, value) in list_exercise) {
                ex.add(value)
            }
            pref.put("template", Gson().toJson( ex ))
            onBackPressed()
        }
        binding.search.doAfterTextChanged {
            val params = HashMap<String?, String?>()
            params["keyword"] = it.toString()
            presenter.fetchExercise(params)
        }

        categoriesAdapter =CategoriesAdapter(arrayListOf(), object: CategoriesAdapter.AdapterListener {
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

        createTemplateAdapter =CreateTemplateAdapter(arrayListOf(), object: CreateTemplateAdapter.AdapterListener {
            override fun onClick(exercise: ExerciseModel.Data) {

            }
        })
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