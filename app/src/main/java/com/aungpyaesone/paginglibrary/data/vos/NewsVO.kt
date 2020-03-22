package com.aungpyaesone.paginglibrary.data.vos

import com.google.gson.annotations.SerializedName

data class NewsVO(
    val title: String,
    @SerializedName("urlToImage") val image: String
) {
}