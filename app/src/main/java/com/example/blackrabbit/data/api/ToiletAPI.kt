package com.example.blackrabbit.data.api

import com.example.blackrabbit.data.api.response.ToiletResponse
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ToiletAPI {


    suspend fun getCurrentLiveBroadcastList(
        @Field("KEY") key: String,
        @Field("TYPE") type: String = "json",
        @Field("SERVICE") service: String = "SearchPublicToiletPOIService",
        @Field("START_INDEX") startIndex: Int, // 페이징 시작번호 입니다 : 데이터 행 시작번호
        @Field("END_INDEX") endIndex: Int, // 페이징 끝번호 입니다 : 데이터 행 끝번호
        @Field("POI_ID") poiId: String, //optional
        @Field("FNAME") fName: String, //optional
        @Field("ANAME") aName: String //optional
    ): ToiletResponse
}