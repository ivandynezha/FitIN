package com.fitin.ui.food

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.fitin.R
import com.fitin.databinding.AdapterExerciseBinding
import com.fitin.preferences.PrefManager
import kotlinx.android.synthetic.main.fragment_goal.view.*


class FoodAdapter (
    var foods: ArrayList<FoodModel.Data?>,
    var listener: AdapterListener

): RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var pref: PrefManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.getContext()
        pref = PrefManager(context)

        return ViewHolder(
            AdapterExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )
    }

    override fun getItemCount() = foods.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foods[position]
        holder.binding.exercise.text = food?.foodName
        holder.binding.key.text = food?.foodName?.get(0).toString()
        holder.itemView.setOnClickListener {
                val inflater = LayoutInflater.from(context)
                val dialogView: View = inflater.inflate(R.layout.dialog_food, null)
                dialogView.findViewById<TextView>(R.id.calories).text = food?.calories.toString()
                dialogView.findViewById<TextView>(R.id.unit).text = food?.unit.toString()
                val portion =  dialogView.findViewById<EditText>(R.id.portion)
                portion.hint = food?.quantity.toString()
                dialogView.findViewById<TextView>(R.id.carbo).text = food?.carbo.toString()
                dialogView.findViewById<TextView>(R.id.fat).text = food?.fat.toString()
                dialogView.findViewById<TextView>(R.id.protein).text = food?.protein.toString()

                val s:String = "There are "+food?.calories+" calories in "+food?.foodName+" \n" +
                        "Calorie details : "+food?.fat.toString()+" fat, "+food?.carbo.toString()+" carb, "+food?.protein.toString()+" prot."

                dialogView.findViewById<TextView>(R.id.naration).text = s
                val dialog = AlertDialog.Builder(context)
                    .setTitle("Add Fodd")
                    .setMessage(food?.foodName)
                    .setPositiveButton("Ok", null)
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
                        food?.portion = portion.text.toString()
                        listener.onClick(food )
                    }
                }


        }
    }

    class ViewHolder(val binding: AdapterExerciseBinding): RecyclerView.ViewHolder(binding.root)

    fun addList(list: List<FoodModel.Data?>) {
        foods.clear()
        foods.addAll(list)
        notifyDataSetChanged()
    }


    interface AdapterListener {
        fun onClick(food: FoodModel.Data?)
    }

}