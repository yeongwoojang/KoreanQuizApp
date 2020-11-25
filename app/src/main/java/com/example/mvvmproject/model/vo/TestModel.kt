package com.example.mvvmproject.model.vo

import com.google.gson.annotations.SerializedName

data class TestModel (@SerializedName("code") var code: Int,
                      @SerializedName("CNT") var score: Int)