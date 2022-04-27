package com.fitin.ui.food

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.fitin.databinding.ActivityFoodBinding
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiClient
import com.fitin.ui.login.LoginData
import com.google.gson.Gson


class FoodActivity : AppCompatActivity(), FoodView {
    private val binding by lazy { ActivityFoodBinding.inflate(layoutInflater) }
    private lateinit var presenter: FoodPresenter
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var pref: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = FoodPresenter(this, ApiClient.getService(),
            PrefManager(this)
        )

        pref = PrefManager(this)
        foodAdapter =FoodAdapter(arrayListOf(), object: FoodAdapter.AdapterListener {
            override fun onClick(food: FoodModel.Data?) {
                val params = HashMap<String?, String?>()
                params["type"] = intent.getStringExtra("type")
                params["portion"] = food?.portion.toString()
                params["idfood"] = food?.idfood.toString()
                Log.e("params",params.toString())
                var gson = Gson()
                var data = gson.fromJson(pref.getString("user_login"), LoginData::class.java)
                params["iduser"] = data.idusers
                presenter.saveFood(params)

            }
        })
        binding.food.adapter = foodAdapter
        binding.search.doAfterTextChanged {
            val params = HashMap<String?, String?>()
            params["keyword"] = it.toString()
            presenter.fetchFood(params)
        }
        val params = HashMap<String?, String?>()
        params["keyword"] = ""
        presenter.fetchFood(params)

    }
    override fun setupListener(){


    }
    override fun viewLoading(loading: Boolean){}
    override fun viewResponse(response: FoodModel){
        foodAdapter.addList(response.data)
    }
    override fun viewSaveResponse(response: FoodModel){
        if (response.metadata?.code==200){
            Toast.makeText(this, "Success Add Food", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }else{
            Toast.makeText(this, "Server Error", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
    }
    override fun viewError(msg: String){}
}