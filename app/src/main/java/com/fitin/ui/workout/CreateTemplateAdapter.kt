package com.fitin.ui.exercise

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.fitin.R
import com.fitin.databinding.AdapterCreateTemplateBinding
import com.fitin.preferences.PrefManager
import com.fitin.ui.login.LoginActivity
import com.fitin.ui.workout.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson


class CreateTemplateAdapter (
    var exercises: ArrayList<CreateTemplateModel.CreateTemplateModelItem>,
    var listener: AdapterListener

): RecyclerView.Adapter<CreateTemplateAdapter.ViewHolder>() {
    private val texts = arrayListOf<TextView>()
    private lateinit var context:Context
    private lateinit var pref:PrefManager
    val list: MutableList<String> = ArrayList()
    private lateinit var setAdapter: SetAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.getContext()
        pref = PrefManager(context)
        setAdapter =SetAdapter(arrayListOf(), object: SetAdapter.AdapterListener {
            override fun onClick(exercise: CreateTemplateModel.CreateTemplateModelItem.Set) {


            }
        })

        return ViewHolder(
            AdapterCreateTemplateBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )
    }

    override fun getItemCount() = exercises.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.binding.exercise.text = exercise.exerciseName
        holder.binding.key.text = exercise.exerciseName?.get(0).toString()
        holder.binding.option.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val bt = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
                val view: View =
                    LayoutInflater.from(context).inflate(R.layout.bottom_sheet, null)
                view.findViewById<LinearLayout>(R.id.remove)
                    .setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            AlertDialog.Builder(context)
                                .setTitle("Remove Ecercise")
                                .setMessage("Are you sure ?")
                                .setPositiveButton("Yes") { dialog, which ->
                                    removeList(holder.adapterPosition)
                                    bt.dismiss()
                                }.show()

                        }
                    })
                view.findViewById<Button>(R.id.btn_addset)
                    .setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            val intent = Intent(context, AddSetActivity::class.java)
                            intent.putExtra("exercise",Gson().toJson(exercise))
                            context.startActivity(intent)
                            bt.dismiss()

                        }
                    })
//                val imageView: ImageView = view.findViewById(R.id.my_image)
//                Glide.with(context).load(primage).into(imageView)
//                val name: TextView = view.findViewById(R.id.txt_prname)
//                name.setText(prname)
//                val price: TextView = view.findViewById(R.id.txt_prprice)
//                price.setText(prprice)
                bt.setContentView(view)
                bt.show()
            }
        })

        holder.binding.set.adapter = setAdapter
        if(exercise.set!=null){
            setAdapter.addList(exercise.set)
        }
//        if(list.contains(exercise.idexercise)){
//            holder.binding.container.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
//        }
//        holder.itemView.setOnClickListener {
////            Log.e("item",exercise.toString())
////            setColor( holder.binding.exercise )
//            listener.onClick(exercise )
//            if(list.contains(exercise.idexercise)){
//                list.remove(exercise.idexercise)
//                holder.binding.container.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
//            }else{
//                list.add(exercise.idexercise.toString())
//                holder.binding.container.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
//            }
//
//        }
    }

    class ViewHolder(val binding: AdapterCreateTemplateBinding): RecyclerView.ViewHolder(binding.root)

    fun addList(list: List<CreateTemplateModel.CreateTemplateModelItem>) {
        exercises.clear()
        exercises.addAll(list)
        notifyDataSetChanged()
    }

    fun removeList(postion:Int) {
        exercises.removeAt(postion)
        val list_exercise: HashMap<String?,ExerciseModel.Data> = HashMap()
        exercises.forEach {
            var json:String = Gson().toJson(it)
            list_exercise.put(it.idexercise, Gson().fromJson(json, ExerciseModel.Data::class.java))
        }
        val ex:ArrayList<ExerciseModel.Data> = ArrayList()
        for ((key, value) in list_exercise) {
            ex.add(value)
        }
        pref.put("template", Gson().toJson( ex ))
        notifyDataSetChanged()
    }



    interface AdapterListener {
        fun onClick(exercises: ExerciseModel.Data)
    }

}