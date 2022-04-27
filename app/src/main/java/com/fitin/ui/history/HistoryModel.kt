package com.fitin.ui.history


import com.google.gson.annotations.SerializedName

data class HistoryModel(
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
        @SerializedName("date_input")
        val dateInput: String?,
        @SerializedName("workout_count")
        val workoutCount: String?,
        @SerializedName("workout")
        val workout: List<Workout?>
    ) {
        data class Workout(
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
            @SerializedName("weight_count")
            val weightCount: String?,
            @SerializedName("exercise")
            val exercise: List<Exercise?>
        ) {
            data class Exercise(
                @SerializedName("idworkout_history_detail")
                val idworkoutHistoryDetail: String?,
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
                val setCount: String?,
                @SerializedName("set")
                val `set`: List<Set?>?
            ) {
                data class Set(
                    @SerializedName("reps")
                    val reps: String?,
                    @SerializedName("weight")
                    val weight: String?,
                    @SerializedName("idexercise")
                    val idexercise: String?
                )
            }
        }
    }
}