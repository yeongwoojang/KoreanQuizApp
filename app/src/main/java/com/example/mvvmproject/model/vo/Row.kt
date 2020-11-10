package com.example.mvvmproject.model.vo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Row(
    @SerializedName("Q_SEQ")
    @Expose
    var q_seq: Double,

    @SerializedName("Q_NAME")
    @Expose
    var q_name: String,

    @SerializedName("Q_OPEN")
    @Expose
    var q_open: Double,

    @SerializedName("A_SEQ")
    @Expose
    var a_seq: Double,

    @SerializedName("A_NAME")
    @Expose
    var a_name: String,

    @SerializedName("A_CORRECT")
    @Expose
    var a_correct: String
)