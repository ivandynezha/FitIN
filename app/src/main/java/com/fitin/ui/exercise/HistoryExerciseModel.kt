package com.fitin.ui.exercise


import com.google.gson.annotations.SerializedName

data class HistoryExerciseModel(
    @SerializedName("metadata")
    val metadata: Metadata?,
    @SerializedName("data")
    val `data`: List<Data?>
) {
    data class Metadata(
        @SerializedName("code")
        val code: Int?,
        @SerializedName("msg")
        val msg: String?
    )

    data class Data(
        @SerializedName("idworkout_history")
        val idworkoutHistory: String?,
        @SerializedName("date_input")
        val dateInput: String?,
        @SerializedName("users_idusers")
        val usersIdusers: String?,
        @SerializedName("workout_name")
        val workoutName: String?,
        @SerializedName("duration")
        val duration: String?,
        @SerializedName("idexercise")
        val idexercise: String?,
        @SerializedName("set")
        val `set`: List<Set?>
    ) {
        data class Set(
            @SerializedName("idset_history")
            val idsetHistory: String?,
            @SerializedName("reps")
            val reps: String?,
            @SerializedName("dumble_weight")
            val dumbleWeight: String?,
            @SerializedName("workout_history_detail_idworkout_history_detail")
            val workoutHistoryDetailIdworkoutHistoryDetail: String?
        )
    }
}