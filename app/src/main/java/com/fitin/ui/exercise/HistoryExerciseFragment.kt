package com.fitin.ui.exercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fitin.databinding.FragmentHistoryExerciseBinding
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiClient
import com.fitin.ui.history.HistoryDetailAdapter
import com.fitin.ui.history.HistoryModel
import com.fitin.ui.login.LoginData
import com.fitin.ui.workout.ExerciseModel
import com.google.gson.Gson

class HistoryExerciseFragment : Fragment(), HistoryExerciseView {

    private var _binding: FragmentHistoryExerciseBinding? = null
    private lateinit var pref: PrefManager
    private lateinit var presenter:HistoryExercisePresenter
    private lateinit var adapter:HistoryExerciseAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryExerciseBinding.inflate(inflater, container, false)
        val root: View = binding.root
        presenter = HistoryExercisePresenter(this,ApiClient.getService(), PrefManager(requireActivity()!!.applicationContext!!))
        pref = PrefManager(requireActivity()!!.applicationContext!!)



        return root
    }

    override fun setupListener(){


    }

    override fun onResume() {
        super.onResume()
        var gson = Gson()
        var data = gson.fromJson(pref.getString("user_login"), LoginData::class.java)
        var data_exercise = Gson().fromJson(pref.getString("detail_exercise"), ExerciseModel.Data::class.java)

        adapter= HistoryExerciseAdapter(arrayListOf(), object: HistoryExerciseAdapter.AdapterListener {
            override fun onClick(exercise: HistoryExerciseModel.Data) {

            }
        })

        binding.history.adapter = adapter
        val params = HashMap<String?, String?>()
        params["iduser"] = data.idusers
        params["idexercise"] = data_exercise.idexercise
        presenter.fetchHistory(params)

    }
    override fun viewLoading(loading: Boolean){}
    override fun viewResponse(response: HistoryExerciseModel){
        adapter.addList( response.data )
    }
    override fun viewError(msg: String){}
}