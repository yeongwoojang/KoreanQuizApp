package com.example.mvvmproject.model.vo

import com.google.gson.annotations.SerializedName
import java.util.*

class TimeLimit  (@SerializedName("code") var code: Int,
                  @SerializedName("limit_time") var limit_time: Date
)