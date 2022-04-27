package com.fitin.ui.food


import com.google.gson.annotations.SerializedName

data class FoodModel(
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
        @SerializedName("idfood")
        val idfood: String?,
        @SerializedName("food_name")
        val foodName: String?,
        @SerializedName("fat")
        val fat: Any?,
        @SerializedName("carbo")
        val carbo: Any?,
        @SerializedName("protein")
        val protein: Any?,
        @SerializedName("calories")
        val calories: Any?,
        @SerializedName("quantity")
        val quantity: Any?,
        @SerializedName("unit")
        val unit: Any?,
        @SerializedName("categories")
        val categories: String?,
        @SerializedName("parent")
        val parent: Any?,
        @SerializedName("portion")
        var portion: Any?
    )
}