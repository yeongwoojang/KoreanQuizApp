package com.example.mvvmproject.model.vo

import com.google.gson.annotations.SerializedName
import org.json.JSONArray

data class QuizDate(
    @SerializedName("code") var code: Int,
    @SerializedName("quiz_no") var quiz_no: Int
)