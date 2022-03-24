package com.fitin.ui.workout


import com.google.gson.annotations.SerializedName

data class WorkoutModelNew(
    @SerializedName("metadata")
    val metadata: Metadata?,
    @SerializedName("data")
    val `data`: List<Data?>?
) {
    data class Metadata(
        @SerializedName("code")
        val code: Int?,
        @SerializedName("msg")
        val msg: String?
    )

    data class Data(
        @SerializedName("idworkout")
        val idworkout: String?,
        @SerializedName("workout_name")
        val workoutName: String?,
        @SerializedName("users_idusers")
        val usersIdusers: String?,
        @SerializedName("detail")
        val detail: Detail?
    ) {
        data class Detail(
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
            @SerializedName("set_count")
            val setCount: String?,
            @SerializedName("set")
            val `set`: List<Set?>?
        ) {
            data class Set(
                @SerializedName("reps")
                val reps: String?,
                @SerializedName("dumble_wight")
                val dumbleWight: String?
            )
        }
    }
}