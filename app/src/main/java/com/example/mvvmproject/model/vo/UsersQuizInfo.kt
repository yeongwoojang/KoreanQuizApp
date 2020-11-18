package com.example.mvvmproject.model.vo

import com.google.gson.annotations.SerializedName

data class UsersQuizInfo(
    @SerializedName("code") var code: Int,
    @SerializedName("score") var score: String,
    @SerializedName("quizSeq") var quizSeq: String
) {
}