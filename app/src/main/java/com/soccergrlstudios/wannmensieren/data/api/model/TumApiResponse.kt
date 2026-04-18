package com.soccergrlstudios.wannmensieren.data.api.model

import com.google.gson.annotations.SerializedName

data class TumApiResponse<T>(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("offset") val offset: Int,
    @SerializedName("next_offset") val nextOffset: Int? = null,
    @SerializedName("hits") val hits: List<T>
)
