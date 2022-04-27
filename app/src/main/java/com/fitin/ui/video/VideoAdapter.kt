package com.fitin.ui.video

import com.fitin.ui.video.*

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.fitin.databinding.AdapterVideoBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fitin.R
import com.fitin.preferences.PrefManager
import com.fitin.services.ApiClient
import com.fitin.ui.login.LoginData
import com.fitin.ui.workout.StartWorkoutActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import java.util.HashMap


class VideoAdapter (
    var videos: ArrayList<VideoModel.Data?>,
    var listener: AdapterListener
): RecyclerView.Adapter<VideoAdapter.ViewHolder>(){
    private lateinit var context: Context
    val list: MutableList<String> = ArrayList()
    private lateinit var pref: PrefManager


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.getContext()
        pref = PrefManager(context)

        return ViewHolder(
            AdapterVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }



    override fun getItemCount() = videos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = videos[position]
        val url:String = "https://img.youtube.com/vi/"+video?.id+"/0.jpg";
        Glide.with(context).load(url).into(holder.binding.thumbnail)
        holder.binding.tittle.text = video?.tittle
        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video?.url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setPackage("com.google.android.youtube");
            context.startActivity(intent)

        }
    }

    class ViewHolder(val binding: AdapterVideoBinding): RecyclerView.ViewHolder(binding.root)

    fun addList(list: List<VideoModel.Data?>) {
        videos.clear()
        videos.addAll(list)
        notifyDataSetChanged()
    }
    fun removeList(postion:Int) {
        videos.removeAt(postion)
        notifyDataSetChanged()
    }


    interface AdapterListener {
        fun onClick(videos: VideoModel)
    }

}