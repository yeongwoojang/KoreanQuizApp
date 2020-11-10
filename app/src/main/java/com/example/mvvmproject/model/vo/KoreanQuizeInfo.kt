package com.example.mvvmproject.model.vo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class KoreanQuizeInfo(
    @SerializedName("KoreanAnswerInfo")
    @Expose
    var koreanAnswerInfo: KoreanAnswerInfo
)