package com.fitin.ui.workout


import com.google.gson.annotations.SerializedName

class CreateTemplateModel : ArrayList<CreateTemplateModel.CreateTemplateModelItem>(){
    data class CreateTemplateModelItem(
        @SerializedName("body_part")
        val bodyPart: String?,
        @SerializedName("categories")
        val categories: String?,
        @SerializedName("exercise_name")
        val exerciseName: String?,
        @SerializedName("idexercise")
        val idexercise: String?,
        @SerializedName("set")
        var set: ArrayList<Set?>
    ){
        data class Set(
            @SerializedName("reps")
            val reps: String?,
            @SerializedName("weight")
            val weight: String?,
            @SerializedName("idexercise")
            val idexercise: String?,
        )
    }
}