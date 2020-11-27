package com.example.mvvmproject.model.vo

import com.google.gson.annotations.SerializedName

data class RankRow(
    @SerializedName("RANKING") var rank : Int,
    @SerializedName("USER_NAME") var userName : String,
    @SerializedName("USER_ID") var userId : String,
    @SerializedName("USER_SCORE") var userScore : Int
)