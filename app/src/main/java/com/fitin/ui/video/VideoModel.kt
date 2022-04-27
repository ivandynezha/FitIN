package com.fitin.ui.video


import com.google.gson.annotations.SerializedName

data class VideoModel(
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
        @SerializedName("idvideo")
        val idvideo: String?,
        @SerializedName("tittle")
        val tittle: String?,
        @SerializedName("url")
        val url: String?,
        @SerializedName("id")
        val id: String?
    )
}