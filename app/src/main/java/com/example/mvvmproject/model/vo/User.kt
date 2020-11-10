package com.example.mvvmproject.model.vo

import com.google.gson.annotations.SerializedName

 data class User(@SerializedName("id") val id: String, @SerializedName("pw") val pw: String)