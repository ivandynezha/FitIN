package com.fitin.ui.history

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.fitin.R
import com.fitin.databinding.AdapterHistoryDetailBinding
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiClient
import com.fitin.ui.workout.StartWorkoutActivity
import com.fitin.ui.workout.deleteResponse
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson

class HistoryDetailAdapter (
    var histories: ArrayList<HistoryModel.Data.Workout?>,
    var listener: AdapterListener
): RecyclerView.Adapter<HistoryDetailAdapter.ViewHolder>(),HistoryDetailView{
    private lateinit var context: Context
    val list: MutableList<String> = ArrayList()
    private lateinit var pref: PrefManager
    private lateinit var presenter: HistoryDetailPresenter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.getContext()
        pref = PrefManager(context)
        presenter = HistoryDetailPresenter(this,ApiClient.getService(), PrefManager(context))
        return ViewHolder(
            AdapterHistoryDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    override fun setupListener(){}
    override fun viewLoading(loading: Boolean){}


    override fun viewResponse(response: deleteResponse){
        removeList(response.data)
        Toast.makeText(context, "Success Delete History", Toast.LENGTH_SHORT).show()
        listener.onClick(response )
    }
    override fun viewError(msg: String){}

    override fun getItemCount() = histories.size

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val history = histories[position]
        holder.binding.workoutname.text = history?.workoutName
        val exercise:List<HistoryModel.Data.Workout.Exercise?>?
        exercise = histories[position]?.exercise
        var text:String
        text = ""
        var set = ""
        var weight_count = 0
        exercise?.forEach {
            text+=it!!.setCount.toString()+" x "+it!!.exerciseName+"\n"
            if(it!!.dumbleWeight.toString().equals("0")){
                set+= it!!.reps+" Reps\n"
            }else{
                set+= it!!.dumbleWeight+" Kg x "+it!!.reps+"\n"
            }
            weight_count += it!!.dumbleWeight!!.toInt()
        }
        holder.binding.duration.text = history?.duration.toString()
        holder.binding.weight.text = weight_count.toString()+" Kg"
        holder.binding.date.text = history?.dateInput
        holder.binding.exercise.text = text
        holder.binding.set.text = set

        holder.itemView.setOnClickListener {
            val bt = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.bottom_sheet_history, null)

            view.findViewById<Button>(R.id.btn_start)
                .setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        val intent = Intent(context, StartWorkoutActivity::class.java)
//                            intent.putExtra("template" ,Gson().toJson(workout.detail))
//                            Log.e("detail",Gson().toJson(workout.detail))
                        pref.put("template", Gson().toJson(history?.exercise))
                        intent.putExtra("workoutname",history?.workoutName)
                        context.startActivity(intent)
                        bt.dismiss()

                    }
                })
            view.findViewById<LinearLayout>(R.id.delete)
                .setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        val dialog = AlertDialog.Builder(context)
                            .setTitle("Delete History")
                            .setMessage("Are you sure delete this history?")
                            .setPositiveButton("Delete", null)
                            .setNegativeButton("Cancel", null)
                            .setCancelable(false)

                            .show()

                        val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        positiveButton.setOnClickListener {
                            val params = HashMap<String?, String?>()
                            params["idworkout"] = history?.idworkoutHistory.toString()
                            params["position"] = position.toString()
                            presenter.removeHistory(params)
                            dialog.dismiss()
                            bt.dismiss()
                        }

                    }
                })

            bt.setContentView(view)
            bt.show()
        }


    }

    class ViewHolder(val binding: AdapterHistoryDetailBinding): RecyclerView.ViewHolder(binding.root)

    fun addList(list: List<HistoryModel.Data.Workout?>) {
        histories.clear()
        histories.addAll(list)
        notifyDataSetChanged()
    }
    fun removeList(postion:Int) {
        histories.removeAt(postion)
        notifyDataSetChanged()
    }


    interface AdapterListener {
        fun onClick(histories: deleteResponse)
    }

}