package com.fitin.ui.workout

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fitin.R
import com.fitin.databinding.AdapterCategoriesBinding


class CategoriesAdapter (
    var categoriess: ArrayList<CategoriesModel.Data>,
    var listener: AdapterListener
): RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    private lateinit var context: Context
    val list: MutableList<String> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.getContext()
        return ViewHolder(
            AdapterCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = categoriess.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categories = categoriess[position]
        holder.binding.categories.text = categories.categories
        holder.itemView.setOnClickListener {
            listener.onClick(categories)
            if(list.contains(categories.categories)){
                list.remove(categories.categories)
                holder.binding.container.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }else{
                list.add(categories.categories.toString())
                holder.binding.container.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
            }
        }

    }

    class ViewHolder(val binding: AdapterCategoriesBinding): RecyclerView.ViewHolder(binding.root)

    fun addList(list: List<CategoriesModel.Data>) {
        categoriess.clear()
        categoriess.addAll(list)
        notifyDataSetChanged()
    }

    interface AdapterListener {
        fun onClick(categoriess: CategoriesModel.Data)
    }

}