package com.fitin.ui.workout


import com.google.gson.annotations.SerializedName

data class FinishModel(
    @SerializedName("metadata")
    val metadata: Metadata?,
    @SerializedName("data")
    val `data`: List<Data?>,
    @SerializedName("workout_name")
    val workoutName: String?,
    @SerializedName("date_workout")
    val dateWorkout: String?
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
        @SerializedName("idset_history")
        val idsetHistory: String?,
        @SerializedName("reps")
        val reps: String?,
        @SerializedName("dumble_weight")
        val dumbleWeight: String?,
        @SerializedName("workout_history_detail_idworkout_history_detail")
        val workoutHistoryDetailIdworkoutHistoryDetail: String?,
        @SerializedName("set_count")
        val setCount: String?
    )
}