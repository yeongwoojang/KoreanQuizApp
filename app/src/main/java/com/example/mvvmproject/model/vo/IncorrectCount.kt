package com.example.mvvmproject.model.vo

import com.google.gson.annotations.SerializedName

data class IncorrectCount(
    @SerializedName("code") var code: Int,
    @SerializedName("incorrectCount") var incorrectCount: Int
)