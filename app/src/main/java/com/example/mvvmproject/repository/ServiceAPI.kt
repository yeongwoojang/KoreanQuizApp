package com.example.mvvmproject.repository

import com.example.mvvmproject.model.vo.KoreanAnswerInfo
import com.example.mvvmproject.model.vo.KoreanQuizeInfo
import com.example.mvvmproject.model.vo.QuizDate
import com.example.mvvmproject.model.vo.RegiRes
import retrofit2.http.*

interface ServiceAPI {
    companion object {
        val BASE_URL: String = "http://ec2-15-164-129-208.ap-northeast-2.compute.amazonaws.com:3000"
        val KOREAN_QUIZE_URL: String =
            "http://openapi.seoul.go.kr:8088/64584b74676a797734366e4d4a576c/"

    }

    @FormUrlEncoded
    @POST("/main/register")
    suspend fun register(@Field("id") id: String, @Field("pw") pw: String): RegiRes

    @POST("/quiz/currentDate")
    suspend fun getCurrentDate() : QuizDate


    @GET("json/KoreanAnswerInfo/1/5/{Q_OPEN}")
    suspend fun getKoreanQuize(
        @Path("Q_OPEN") q_open: String
    ): KoreanQuizeInfo


}