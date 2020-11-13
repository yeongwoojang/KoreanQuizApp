package com.example.mvvmproject.repository

import com.example.mvvmproject.model.vo.IdCheckRes
import com.example.mvvmproject.model.vo.KoreanQuizeInfo
import com.example.mvvmproject.model.vo.QuizDate
import com.example.mvvmproject.model.vo.RegsRes
import retrofit2.http.*

interface ServiceAPI {
    companion object {
        val BASE_URL: String = "http://ec2-15-164-129-208.ap-northeast-2.compute.amazonaws.com:3000"
        val KOREAN_QUIZE_URL: String =
            "http://openapi.seoul.go.kr:8088/64584b74676a797734366e4d4a576c/"
    }

    @FormUrlEncoded
    @POST("/korQuiz/register")
    suspend fun register(
        @Field("USER_NAME") name: String,
        @Field("USER_ID") id: String,
        @Field("USER_PW") pw: String,
        @Field("USER_PHONE") phone: String
    ): RegsRes

    @FormUrlEncoded
    @POST("/korQuiz/idChk")
    suspend fun idChk(
        @Field("USER_ID") id: String
    ): RegsRes

    @FormUrlEncoded
    @POST("/korQuiz/login")
    suspend fun login(
        @Field("USER_ID") id: String,
        @Field("USER_PW") pw: String
    ): RegsRes

    @POST("/quiz/quizNo")
    suspend fun getQuizNo(): QuizDate


    @GET("json/KoreanAnswerInfo/1/476")
    suspend fun getKoreanQuiz(): KoreanQuizeInfo


}