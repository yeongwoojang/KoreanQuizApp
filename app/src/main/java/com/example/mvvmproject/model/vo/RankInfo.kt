package com.example.mvvmproject.model.vo

import com.google.gson.annotations.SerializedName

data class RankInfo(
    @SerializedName("code") var code: Int,
    @SerializedName("rankJsonArray") var rankJsonArray: List<RankRow>
)