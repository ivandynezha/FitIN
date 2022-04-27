package com.fitin.ui.exercise


import com.google.gson.annotations.SerializedName

data class AddResponse(
    @SerializedName("metadata")
    val metadata: Metadata?,
    @SerializedName("data")
    val `data`: Data?
) {
    data class Metadata(
        @SerializedName("code")
        val code: Int?,
        @SerializedName("msg")
        val msg: String?
    )

    data class Data(
        @SerializedName("exercise_name")
        val exerciseName: String?,
        @SerializedName("body_part")
        val bodyPart: String?,
        @SerializedName("categories")
        val categories: String?,
        @SerializedName("users_idusers")
        val usersIdusers: String?
    )
}