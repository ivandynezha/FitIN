package com.fitin.ui.workout


import com.google.gson.annotations.SerializedName

data class WorkoutResponse(
    @SerializedName("metadata")
    val metadata: Metadata?,
    @SerializedName("data")
    val `data`: List<Data?>?,
    @SerializedName("workout_name")
    val workoutName: String?
) {
    data class Metadata(
        @SerializedName("code")
        val code: Int?,
        @SerializedName("msg")
        val msg: String?
    )

    data class Data(
        @SerializedName("body_part")
        val bodyPart: String?,
        @SerializedName("categories")
        val categories: String?,
        @SerializedName("exercise_name")
        val exerciseName: String?,
        @SerializedName("idexercise")
        val idexercise: String?,
        @SerializedName("set")
        val `set`: List<Set?>?
    ) {
        data class Set(
            @SerializedName("idexercise")
            val idexercise: String?,
            @SerializedName("reps")
            val reps: String?,
            @SerializedName("weight")
            val weight: String?
        )
    }
}