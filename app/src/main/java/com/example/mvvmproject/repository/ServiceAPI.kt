package com.example.mvvmproject.repository

import com.example.mvvmproject.model.vo.*
import retrofit2.http.*

interface ServiceAPI {
    companion object {
        val BASE_URL: String = "http://ec2-15-164-129-208.ap-northeast-2.compute.amazonaws.com:3000"
        val KOREAN_QUIZE_URL: String =
            "http://openapi.seoul.go.kr:8088/6b62436b676a797739386c494c5a58/"
    }

    @FormUrlEncoded
    @POST("/korQuiz/register")
    suspend fun register(
        @Field("USER_NAME") name: String,
        @Field("USER_ID") id: String,
        @Field("USER_PW") pw: String,
        @Field("USER_PHONE") phone: String
    ): RegsRes

    @GET("/korQuiz/idChk/{USER_ID}")
    suspend fun idChk(
        @Path("USER_ID") id: String
    ): RegsRes

    @FormUrlEncoded
    @POST("/korQuiz/login")
    suspend fun login(
        @Field("USER_ID") id: String,
        @Field("USER_PW") pw: String
    ): RegsRes

    @PUT("/korQuiz/scoreUpdate")
    suspend fun updateScore(): String

    @GET("/korQuiz/getScore")
    suspend fun updateView(): UsersQuizInfo


    @POST("/quiz/quizNo")
    suspend fun getQuizNo(): QuizDate

    @GET("/korQuiz/incorrectCnt")
    suspend fun getIncorrectCount(): IncorrectCount

    @PUT("KorQuiz/plusIncorrectCnt")
    suspend fun putIncorrectCount(): String

    @PUT("KorQuiz/restartIncorrectCnt")
    suspend fun restartIncorrectCount(): String


    @GET("json/KoreanAnswerInfo/1/476")
    suspend fun getKoreanQuiz(): KoreanQuizeInfo

    @GET("KorQuiz/getTimeLimit")
    suspend fun getTimeLimit(): TimeLimit

    @PUT("KorQuiz/putTimeLimit")
    suspend fun putTimeLimit(): String

    @PUT("KorQuiz/deleteTimeLimit")
    suspend fun deleteTimeLimit() : String

    @GET("KorQuiz/rank")
    suspend fun getRanking() :RankInfo
}