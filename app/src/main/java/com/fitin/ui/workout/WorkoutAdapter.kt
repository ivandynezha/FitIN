package com.fitin.ui.workout

import android.content.Context
import android.content.Intent
import android.util.Log
import com.fitin.databinding.AdapterWorkoutBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.fitin.R
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiClient
import com.fitin.ui.login.LoginData
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import java.util.HashMap


class WorkoutAdapter (
    var workouts: ArrayList<WorkoutModel.WorkoutData>,
    var listener: AdapterListener
): RecyclerView.Adapter<WorkoutAdapter.ViewHolder>(), WorkoutAdapterView {
    private lateinit var context: Context
    val list: MutableList<String> = ArrayList()
    private lateinit var pref: PrefManager
    private lateinit var presenter: WorkoutAdapterPresenter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.getContext()
        pref = PrefManager(context)
        presenter = WorkoutAdapterPresenter(this,ApiClient.getService(), PrefManager(context))
        return ViewHolder(
            AdapterWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun setupListener(){}
    override fun viewLoading(loading: Boolean){}
    override fun viewResponse(response: deleteResponse){
        removeList(response.data)
        Toast.makeText(context, "Success Delete Template", Toast.LENGTH_SHORT).show()


    }
    override fun viewError(msg: String){}

    override fun getItemCount() = workouts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val workout = workouts[position]
        holder.binding.workoutname.text = workout.workoutName
        val exercise:List<WorkoutModel.WorkoutData.Detail?>?
        exercise = workouts[position].detail
        var text:String
        text = ""
        exercise?.forEach {
            text+=it!!.setCount.toString()+" x "+it!!.exerciseName+"\n"
        }
        holder.binding.exercise.text = text
        holder.binding.option.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val bt = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
                val view: View =
                    LayoutInflater.from(context).inflate(R.layout.bottom_sheet_workout, null)
                view.findViewById<LinearLayout>(R.id.remove)
                    .setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            AlertDialog.Builder(context)
                                .setTitle("Remove Template")
                                .setMessage("Are you sure ?")
                                .setPositiveButton("Yes") { dialog, which ->
                                    bt.dismiss()
                                    val params = HashMap<String?, String?>()
                                    params["idworkout"] = workout.idworkout
                                    params["position"] = holder.adapterPosition.toString()
                                    presenter.removeWorkout(params)

                                }.show()

                        }
                    })
                view.findViewById<Button>(R.id.btn_start)
                    .setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
//                            val intent = Intent(context, StartWorkoutActivity::class.java)
//                            intent.putExtra("template" ,Gson().toJson(workout.detail))
//                            Log.e("detail",Gson().toJson(workout.detail))
//                            context.startActivity(intent)
                            bt.dismiss()

                        }
                    })

                bt.setContentView(view)
                bt.show()
            }
        })
    }

    class ViewHolder(val binding: AdapterWorkoutBinding): RecyclerView.ViewHolder(binding.root)

    fun addList(list: List<WorkoutModel.WorkoutData>) {
        workouts.clear()
        workouts.addAll(list)
        notifyDataSetChanged()
    }
    fun removeList(postion:Int) {
        workouts.removeAt(postion)
        notifyDataSetChanged()
    }


    interface AdapterListener {
        fun onClick(workouts: WorkoutModel.WorkoutData)
    }

}