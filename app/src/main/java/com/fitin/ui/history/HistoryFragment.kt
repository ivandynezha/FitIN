package com.fitin.ui.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.fitin.databinding.FragmentHistoryBinding
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiClient
import com.fitin.ui.login.LoginData
import com.fitin.ui.history.*
import com.fitin.ui.workout.HistoryPresenter
import com.fitin.ui.workout.HistoryView
import com.fitin.ui.workout.deleteResponse
import com.google.gson.Gson


class HistoryFragment : Fragment(), HistoryView {

    private var _binding: FragmentHistoryBinding? = null
    private lateinit var presenter: HistoryPresenter
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var pref: PrefManager
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        pref = PrefManager(requireContext()!!.applicationContext)
        presenter = HistoryPresenter(this,ApiClient.getService(), PrefManager(requireContext()!!.applicationContext))
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setupListener() {
        historyAdapter = HistoryAdapter(arrayListOf(), object: HistoryAdapter.AdapterListener {
            override fun onClick(history: deleteResponse) {
                val params = HashMap<String?, String?>()
                var gson = Gson()
                var data = gson.fromJson(pref.getString("user_login"), LoginData::class.java)
                params["iduser"] = data.idusers
                presenter.fetchHistory(params)
            }
        })
        binding.history.adapter = historyAdapter

    }




    override fun onResume() {
        super.onResume()
        val params = HashMap<String?, String?>()
        var gson = Gson()
        var data = gson.fromJson(pref.getString("user_login"), LoginData::class.java)
        params["iduser"] = data.idusers
        presenter.fetchHistory(params)
    }

    override fun viewLoading(loading: Boolean) {
//        binding.swipe.isRefreshing = loading
    }

    override fun viewResponse(response: HistoryModel) {
        Log.e("res",response.data.toString())
        historyAdapter.addList( response.data )
    }



    override fun viewError(msg: String) {
//        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
//        binding.swipe.isRefreshing = false
    }
}