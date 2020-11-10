package com.example.mvvmproject.model.vo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class KoreanAnswerInfo(
    @SerializedName("list_total_count")
    @Expose
    var listTotalCount :Int,
    @SerializedName("RESULT")
    @Expose
    var result: Result,
    @SerializedName("row")
    @Expose
    var row: List<Row>
)