package com.fitin.ui.food

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.fitin.R
import com.fitin.databinding.AdapterFoodDiaryBinding
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiClient
import com.fitin.ui.login.LoginData
import com.fitin.ui.workout.StartWorkoutActivity
import com.fitin.ui.workout.deleteResponse
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt


class FoodDiaryAdapter (
    var food: ArrayList<FoodDiaryModel.Data.Food?>,
    var listener: AdapterListener
): RecyclerView.Adapter<FoodDiaryAdapter.ViewHolder>(), FoodDiaryAdapterView {
    private lateinit var context: Context
    val list: MutableList<String> = ArrayList()
    var cal: Float = 0F
    private lateinit var pref: PrefManager
    private lateinit var presenter: FoodDiaryAdapterPresenter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.getContext()
        pref = PrefManager(context)
        presenter = FoodDiaryAdapterPresenter(this,ApiClient.getService(), PrefManager(context))
        return ViewHolder(
            AdapterFoodDiaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun setupListener(){}
    override fun viewLoading(loading: Boolean){}
    override fun viewResponse(response: deleteResponse){
        removeList(response.data)
        Toast.makeText(context, "Success Delete Food", Toast.LENGTH_SHORT).show()
        listener.onClick(response )

    }

    override fun editResponse(response: deleteResponse){
        Toast.makeText(context, "Success Update Food", Toast.LENGTH_SHORT).show()
        listener.onClick(response )

    }
    override fun viewError(msg: String){}

    override fun getItemCount() = food.size



    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val food = food[position]
        val carbo:Float = food?.carbo!!.toFloat()*food?.portion!!.toFloat()/food?.quantity!!.toFloat()
        val calories:Float = food?.calories!!.toFloat()*food?.portion!!.toFloat()/food?.quantity!!.toFloat()
        val fat:Float = food?.fat!!.toFloat()*food?.portion!!.toFloat()/food?.quantity!!.toFloat()
        val protein:Float = food?.protein!!.toFloat()*food?.portion!!.toFloat()/food?.quantity!!.toFloat()

        val cal:Int = Math.round(calories)
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        holder.binding.food.text = food?.foodName
        holder.binding.portion.text = food?.portion+" "+food?.unit
        holder.binding.carbo.text = df.format(carbo).toString()
        holder.binding.cal.text = cal.toString()
        holder.binding.fat.text = df.format(fat).toString()
        holder.binding.protein.text = df.format(protein).toString()
        holder.itemView.setOnClickListener {
            val bt = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.bottom_sheet_food, null)

            view.findViewById<Button>(R.id.btn_edit)
                .setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        val inflater = LayoutInflater.from(context)
                        val dialogView: View = inflater.inflate(R.layout.dialog_food, null)
                        dialogView.findViewById<TextView>(R.id.calories).text = food?.calories.toString()
                        dialogView.findViewById<TextView>(R.id.unit).text = food?.unit.toString()
                        val portion =  dialogView.findViewById<EditText>(R.id.portion)
                        portion.hint = food?.portion.toString()
                        portion.setText(food?.portion.toString())
                        dialogView.findViewById<TextView>(R.id.carbo).text = food?.carbo.toString()
                        dialogView.findViewById<TextView>(R.id.fat).text = food?.fat.toString()
                        dialogView.findViewById<TextView>(R.id.protein).text = food?.protein.toString()

                        val s:String = "There are "+food?.calories+" calories in "+food?.foodName+" \n" +
                                "Calorie details : "+food?.fat.toString()+" fat, "+food?.carbo.toString()+" carb, "+food?.protein.toString()+" prot."

                        dialogView.findViewById<TextView>(R.id.naration).text = s
                        val dialog = AlertDialog.Builder(context)
                            .setTitle("Edit Food")
                            .setMessage(food?.foodName)
                            .setPositiveButton("Update", null)
                            .setNegativeButton("Cancel", null)
                            .setCancelable(false)
                            .setView(dialogView)
                            .show()

                        val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        positiveButton.setOnClickListener {
                            if(portion.text.toString().trim().length == 0){
                                portion.error = "Filled this field"
                            }else{
                                Toast.makeText(context, "Wait...", Toast.LENGTH_SHORT).show()
                                it.isEnabled = false
                                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).isEnabled = false
                                val params = HashMap<String?, String?>()
                                params["iddetail"] = food?.iddetail.toString()
                                params["portion"] = portion.text.toString()
                                presenter.updateFoodDiary(params)
                                dialog.dismiss()
                            }
                        }
                        bt.dismiss()

                    }
                })
            view.findViewById<LinearLayout>(R.id.delete)
                .setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        val dialog = AlertDialog.Builder(context)
                        .setTitle("Delete Food")
                        .setMessage("Are you sure delete "+food?.foodName+"?")
                        .setPositiveButton("Delete", null)
                        .setNegativeButton("Cancel", null)
                        .setCancelable(false)

                        .show()

                        val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        positiveButton.setOnClickListener {
                            val params = HashMap<String?, String?>()
                            params["iddetail"] = food?.iddetail.toString()
                            params["position"] = position.toString()
                            presenter.removeFoodDiary(params)
                            dialog.dismiss()
                        }
                        bt.dismiss()

                    }
                })

            bt.setContentView(view)
            bt.show()



        }


    }

    class ViewHolder(val binding: AdapterFoodDiaryBinding): RecyclerView.ViewHolder(binding.root)

    fun addList(list: List<FoodDiaryModel.Data.Food?>) {
        food.clear()
        food.addAll(list)
        notifyDataSetChanged()
    }
    fun removeList(postion:Int) {
        food.removeAt(postion)
        notifyDataSetChanged()
    }


    interface AdapterListener {
        fun onClick(response: deleteResponse?)

    }



}