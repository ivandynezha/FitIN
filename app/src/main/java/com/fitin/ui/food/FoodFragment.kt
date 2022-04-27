package com.fitin.ui.food

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fitin.R
import com.fitin.databinding.FragmentFoodBinding
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiClient
import com.fitin.ui.login.LoginData
import com.fitin.ui.workout.ExerciseActivity
import com.fitin.ui.workout.deleteResponse
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.gson.Gson
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt


class FoodFragment : Fragment(), FoodDiaryView {

    private var _binding: FragmentFoodBinding? = null
    private lateinit var presenter: FoodDiaryPresenter
    private lateinit var foodDiaryBreakfastAdapter: FoodDiaryAdapter
    private lateinit var foodDiaryLunchAdapter: FoodDiaryAdapter
    private lateinit var foodDiaryDinnerAdapter: FoodDiaryAdapter
    private lateinit var foodDiarySnackAdapter: FoodDiaryAdapter
    private lateinit var pref: PrefManager
    private val binding get() = _binding!!
    private lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFoodBinding.inflate(inflater, container, false)
        val root: View = binding.root
        presenter = FoodDiaryPresenter(this, ApiClient.getService(),
            PrefManager(requireContext()!!.applicationContext)
        )

        pref = PrefManager(requireContext()!!.applicationContext)

        val params = HashMap<String?, String?>()
        var gson = Gson()
        var data = gson.fromJson(pref.getString("user_login"), LoginData::class.java)
        params["iduser"] = data.idusers


        foodDiaryBreakfastAdapter =FoodDiaryAdapter(arrayListOf(), object: FoodDiaryAdapter.AdapterListener {
            override fun onClick(response: deleteResponse?) {
                presenter.fetchFoodDiary(params)
            }
        })
        binding.breakfast.adapter = foodDiaryBreakfastAdapter

        foodDiaryLunchAdapter =FoodDiaryAdapter(arrayListOf(), object: FoodDiaryAdapter.AdapterListener {
            override fun onClick(response: deleteResponse?) {
                presenter.fetchFoodDiary(params)
            }
        })
        binding.lunch.adapter = foodDiaryLunchAdapter

        foodDiaryDinnerAdapter =FoodDiaryAdapter(arrayListOf(), object: FoodDiaryAdapter.AdapterListener {
            override fun onClick(response: deleteResponse?) {
                presenter.fetchFoodDiary(params)
            }
        })
        binding.dinner.adapter = foodDiaryDinnerAdapter

        foodDiarySnackAdapter =FoodDiaryAdapter(arrayListOf(), object: FoodDiaryAdapter.AdapterListener {
            override fun onClick(response: deleteResponse?) {
                presenter.fetchFoodDiary(params)
            }
        })
        binding.snack.adapter = foodDiarySnackAdapter


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pieChart = view.findViewById(R.id.piechart)
        setupPieChart()
    }

    override fun onResume() {
        super.onResume()
        val params = HashMap<String?, String?>()
        var gson = Gson()
        var data = gson.fromJson(pref.getString("user_login"), LoginData::class.java)
        params["iduser"] = data.idusers
        presenter.fetchFoodDiary(params)
    }

    private fun setupPieChart() {
        pieChart.isDrawHoleEnabled = true
        pieChart.setUsePercentValues(false)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.centerText = "0 Cal"
        pieChart.setCenterTextSize(20f)
        pieChart.description.isEnabled = false
        val l = pieChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = false
    }

    private fun loadPieChartData(carb:Float,pro:Float,fat:Float) {
        val entries: ArrayList<PieEntry> = ArrayList()
        if(carb==0f){
            entries.add(PieEntry(carb, ""))
        }else{
            entries.add(PieEntry(carb, "Carb"))
        }
        if(pro==0f){
            entries.add(PieEntry(pro, ""))
        }else{
            entries.add(PieEntry(pro, "Protein (gr)"))
        }
        if(fat==0f){
            entries.add(PieEntry(fat, ""))
        }else{
            entries.add(PieEntry(fat, "Fat (gr)"))
        }

        val colors: ArrayList<Int> = ArrayList()
//        for (color in ColorTemplate.MATERIAL_COLORS) {
//            colors.add(color)
//        }
//        for (color in ColorTemplate.VORDIPLOM_COLORS) {
//            colors.add(color)
//        }
        colors.add(Color.parseColor("#03a678"))
        colors.add(Color.parseColor("#f9bf3b"))
        colors.add(Color.parseColor("#c44d56"))
        colors.add(Color.parseColor("#ececec"))

        val dataSet = PieDataSet(entries, "Expense Category")
        dataSet.colors = colors

        val data = PieData(dataSet)
        if(carb == 0f && pro==0f && fat==0f){
            data.setDrawValues(false)
            pieChart.setEntryLabelColor(Color.TRANSPARENT)
            entries.add(PieEntry(1f, ""))
        }else{
            data.setDrawValues(true)
        }
        data.setValueFormatter(MyCustomValueFormatter())
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)


        pieChart.data = data
        pieChart.invalidate()
        pieChart.animateY(1400, Easing.EaseInOutQuad)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun setupListener() {
        binding.addBreakfast.setOnClickListener {
            val intent = Intent(requireContext()!!.applicationContext, FoodActivity::class.java)
            intent.putExtra("type","1")
            startActivity(intent)
        }
        binding.addLunch.setOnClickListener {
            val intent = Intent(requireContext()!!.applicationContext, FoodActivity::class.java)
            intent.putExtra("type","2")
            startActivity(intent)
        }
        binding.addDinner.setOnClickListener {
            val intent = Intent(requireContext()!!.applicationContext, FoodActivity::class.java)
            intent.putExtra("type","3")
            startActivity(intent)
        }
        binding.addSnack.setOnClickListener {
            val intent = Intent(requireContext()!!.applicationContext, FoodActivity::class.java)
            intent.putExtra("type","4")
            startActivity(intent)
        }

    }



    override fun viewLoading(loading: Boolean) {
//        binding.swipe.isRefreshing = loading
    }

    override fun viewResponse(response: FoodDiaryModel) {

        pieChart.centerText = "Today \n"+response.resume?.calories+" Cal"
        loadPieChartData(response.resume?.carbo!!.toFloat(),response.resume?.protein!!.toFloat(),response.resume?.fat!!.toFloat())
        val data:List<FoodDiaryModel.Data?> = response.data
        foodDiaryBreakfastAdapter.addList(data[0]!!.food)
        binding.breakfastCal.text = data[0]!!.cal+" Cal"

        foodDiaryLunchAdapter.addList(data[1]!!.food)
        binding.lunchCal.text = data[1]!!.cal+" Cal"

        foodDiaryDinnerAdapter.addList(data[2]!!.food)
        binding.dinnerCal.text = data[2]!!.cal+" Cal"

        foodDiarySnackAdapter.addList(data[3]!!.food)
        binding.snackCal.text = data[3]!!.cal+" Cal"

    }



    override fun viewError(msg: String) {
    }
}

class MyCustomValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return if (value == 0f) {
            ""
        } else {
            value.roundToInt().toString() + " "
        }
    }
}