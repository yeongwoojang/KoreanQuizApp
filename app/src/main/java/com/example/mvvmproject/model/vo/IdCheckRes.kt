package com.example.mvvmproject.model.vo

import com.google.gson.annotations.SerializedName

data class IdCheckRes(@SerializedName("idCheckCode") var code : Int, @SerializedName("message") var message : String) {
}