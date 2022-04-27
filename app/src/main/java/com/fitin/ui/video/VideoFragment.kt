package com.fitin.ui.video

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fitin.R
import com.fitin.databinding.FragmentVideoBinding
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiClient

class VideoFragment : Fragment(), VideoView {

    private var _binding: FragmentVideoBinding? = null
    private lateinit var presenter: VideoPresenter
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var pref: PrefManager

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        presenter = VideoPresenter(this, ApiClient.getService(),
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
        videoAdapter = VideoAdapter(arrayListOf(), object: VideoAdapter.AdapterListener {
            override fun onClick(workout: VideoModel) {

            }
        })
        binding.video.adapter = videoAdapter

    }



    override fun viewLoading(loading: Boolean) {
//        binding.swipe.isRefreshing = loading
    }

    override fun viewResponse(response: VideoModel) {
        Log.e("res",response.data.toString())
        videoAdapter.addList( response.data )
    }



    override fun viewError(msg: String) {
    }
}