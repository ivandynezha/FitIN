package com.fitin.ui.workout


import com.google.gson.annotations.SerializedName

data class ExerciseModel(
    @SerializedName("metadata")
    val metadata: Metadata?,
    @SerializedName("data")
    val `data`: List<Data>
) {
    data class Metadata(
        @SerializedName("code")
        val code: Int?,
        @SerializedName("msg")
        val msg: String?
    )

    data class Data(
        @SerializedName("idexercise")
        val idexercise: String?,
        @SerializedName("exercise_name")
        val exerciseName: String?,
        @SerializedName("body_part")
        val bodyPart: String?,
        @SerializedName("categories")
        val categories: String?,
        @SerializedName("about")
        val about: String?,
        @SerializedName("set")
        val set: List<Set?>?
    ){
        data class Set(
            @SerializedName("reps")
            val reps: String?,
            @SerializedName("dumble_wight")
            val dumbleWeight: String?,
        )
    }
}