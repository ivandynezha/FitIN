package com.fitin.ui.workout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fitin.databinding.FragmentWorkoutBinding
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiClient
import com.fitin.ui.login.LoginActivity
import com.fitin.ui.login.LoginData
import com.google.gson.Gson

class WorkoutFragment : Fragment(), WorkoutView {

    private var _binding: FragmentWorkoutBinding? = null
    private lateinit var presenter: WorkoutPresenter
    private lateinit var workoutAdapter: WorkoutAdapter
    private lateinit var pref:PrefManager
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.startBlank.setOnClickListener{
            val intent = Intent(requireActivity()!!.applicationContext, BlankWorkoutActivity::class.java)
            startActivity(intent)
        }
        binding.createTemplate.setOnClickListener{
            val intent = Intent(requireActivity()!!.applicationContext, CreateTemplateActivity::class.java)
            startActivity(intent)
        }
        presenter = WorkoutPresenter(this, ApiClient.getService(),
            PrefManager(requireContext()!!.applicationContext)
        )
        pref = PrefManager(requireContext()!!.applicationContext)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setupListener() {
        workoutAdapter =WorkoutAdapter(arrayListOf(), object: WorkoutAdapter.AdapterListener {
            override fun onClick(workout: WorkoutModel.WorkoutData) {

            }
        })
        binding.mytemplate.adapter = workoutAdapter

    }

    override fun onResume() {
        super.onResume()
        val params = HashMap<String?, String?>()
        var gson = Gson()
        var data = gson.fromJson(pref.getString("user_login"), LoginData::class.java)
        params["iduser"] = data.idusers
        presenter.fetchWorkout(params)
    }

    override fun workoutLoading(loading: Boolean) {
//        binding.swipe.isRefreshing = loading
    }

    override fun workoutResponse(response: WorkoutModel) {
        Log.e("res",response.data.toString())
        workoutAdapter.addList( response.data )
    }



    override fun workoutError(msg: String) {
//        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
//        binding.swipe.isRefreshing = false
    }
}