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

    @POST("/quiz/quizNo")
    suspend fun getQuizNo(): QuizDate

    @FormUrlEncoded
    @POST("/quiz/input")
    suspend fun insertQuiz(
        @Field("q_name") q_name: List<String>,
        @Field("a_one") a_one: List<String>,
        @Field("a_two") a_two: List<String>,
        @Field("a_three") a_three: List<String>,
        @Field("a_four") a_four: List<String>,
        @Field("correct_a") collect_a: List<Int>
    )


    @GET("json/KoreanAnswerInfo/1/476")
    suspend fun getKoreanQuiz(): KoreanQuizeInfo


}