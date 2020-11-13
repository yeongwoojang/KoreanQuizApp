package com.example.mvvmproject.model.vo

import com.google.gson.annotations.SerializedName

data class RegsRes (@SerializedName("code") var code : Int, @SerializedName("message") var message : String)