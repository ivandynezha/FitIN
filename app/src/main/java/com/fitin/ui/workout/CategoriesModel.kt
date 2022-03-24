package com.fitin.ui.workout


import com.google.gson.annotations.SerializedName

data class CategoriesModel(
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
        @SerializedName("categories")
        val categories: String?
    )
}