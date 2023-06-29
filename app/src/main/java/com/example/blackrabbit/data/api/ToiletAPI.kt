package com.example.blackrabbit.data.api

import com.example.blackrabbit.R
import com.example.blackrabbit.data.api.response.ToiletResponse
import com.example.blackrabbit.di.KoinApplication
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ToiletAPI {

    @GET("/{KEY}/{TYPE}/{SERVICE}/{START_INDEX}/{END_INDEX}")
    suspend fun getToiletList(
        @Path("KEY") key: String = KoinApplication.string(R.string.s_public_toilet_api_key),
        @Path("TYPE") type: String = "json",
        @Path("SERVICE") service: String = "GeoInfoPublicToilet",
        @Path("START_INDEX") startIndex: Int = 1, // 페이징 시작번호 입니다 : 데이터 행 시작번호
        @Path("END_INDEX") endIndex: Int = 10, // 페이징 끝번호 입니다 : 데이터 행 끝번호
    ): ToiletResponse
}