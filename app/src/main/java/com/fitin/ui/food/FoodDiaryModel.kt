package com.fitin.ui.food


import com.google.gson.annotations.SerializedName

data class FoodDiaryModel(
    @SerializedName("metadata")
    val metadata: Metadata?,
    @SerializedName("data")
    val `data`: List<Data?>,
    @SerializedName("resume")
    val resume: Resume?
) {
    data class Metadata(
        @SerializedName("code")
        val code: Int?,
        @SerializedName("msg")
        val msg: String?
    )

    data class Data(
        @SerializedName("idfood_diary_type")
        val idfoodDiaryType: String?,
        @SerializedName("type")
        val type: String?,
        @SerializedName("cal")
        val cal: String?,
        @SerializedName("food")
        val food: List<Food?>
    ) {
        data class Food(
            @SerializedName("iddetail")
            val iddetail: String?,
            @SerializedName("idfood")
            val idfood: String?,
            @SerializedName("food_name")
            val foodName: String?,
            @SerializedName("fat")
            val fat: String?,
            @SerializedName("carbo")
            val carbo: String?,
            @SerializedName("protein")
            val protein: String?,
            @SerializedName("calories")
            val calories: String?,
            @SerializedName("quantity")
            val quantity: String?,
            @SerializedName("unit")
            val unit: String?,
            @SerializedName("categories")
            val categories: String?,
            @SerializedName("parent")
            val parent: String?,
            @SerializedName("portion")
            var portion: String?
        )
    }

    data class Resume(
        @SerializedName("protein")
        val protein: String?,
        @SerializedName("fat")
        val fat: String?,
        @SerializedName("calories")
        val calories: String?,
        @SerializedName("carbo")
        val carbo: String?
    )
}