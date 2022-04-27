package com.fitin.ui.exercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fitin.databinding.FragmentAboutBinding
import com.fitin.preferences.PrefManager
import com.fitin.ui.workout.CreateTemplateModel
import com.fitin.ui.workout.ExerciseModel
import com.google.gson.Gson

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private lateinit var pref: PrefManager

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val root: View = binding.root
        pref = PrefManager(requireActivity()!!.applicationContext!!)
        var data = Gson().fromJson(pref.getString("detail_exercise"), ExerciseModel.Data::class.java)
        binding.about.text = data.about
        return root
    }
}