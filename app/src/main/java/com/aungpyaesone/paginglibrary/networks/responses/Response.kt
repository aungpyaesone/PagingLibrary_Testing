package com.aungpyaesone.paginglibrary.networks.responses

import com.aungpyaesone.paginglibrary.data.vos.NewsVO
import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("articles")val news: List<NewsVO>
) {
}