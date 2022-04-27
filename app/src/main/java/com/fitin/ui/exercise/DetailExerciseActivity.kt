package com.fitin.ui.exercise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.fitin.R
import com.fitin.databinding.ActivityDetailExerciseBinding
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiClient
import com.fitin.ui.login.LoginData
import com.fitin.ui.workout.ExerciseModel
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_exercise.*

class DetailExerciseActivity : AppCompatActivity(), CreateExerciseView {

    private val binding by lazy { ActivityDetailExerciseBinding.inflate(layoutInflater) }
    private lateinit var pref:PrefManager
    private lateinit var presenter: CreateExercisePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = CreateExercisePresenter(this,ApiClient.getService(), PrefManager(this))
        tabLayout.addTab(tabLayout.newTab().setText("About"))
        tabLayout.addTab(tabLayout.newTab().setText("History"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = DetailAdapter(this, supportFragmentManager,
            tabLayout.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        pref = PrefManager(this)

        var data = Gson().fromJson(pref.getString("detail_exercise"), ExerciseModel.Data::class.java)
        supportActionBar?.setTitle(data.exerciseName)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var data = Gson().fromJson(pref.getString("detail_exercise"), ExerciseModel.Data::class.java)
        if(data.iduser!=null && data.type!=null){
            menuInflater.inflate(R.menu.exercise_edit_menu,menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId== R.id.edit){
            val intent = Intent(applicationContext, EditExerciseActivity::class.java)
            startActivity(intent)
        }
        if(item.itemId== R.id.delete){
            val dialog = AlertDialog.Builder(this)
                .setTitle("Delete Exercise")
                .setMessage("Are sure delete this exercise?")
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .setCancelable(false)
                .show()

            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                var gson = Gson()
                var data = gson.fromJson(pref.getString("detail_exercise"), ExerciseModel.Data::class.java)
                val params = HashMap<String?, String?>()
                params["idexercise"] = data.idexercise
                presenter.deleteExercise(params)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun setupListener(){}
    override fun viewLoading(loading: Boolean){}
    override fun viewResponse(response: AddResponse){
        if (response.metadata?.code==200){
            Toast.makeText(this,"Success Delete Exercise",Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
    }

    override fun viewError(msg: String){}
}