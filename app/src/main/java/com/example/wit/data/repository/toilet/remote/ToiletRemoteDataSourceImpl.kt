package com.example.wit.data.repository.toilet.remote

import com.example.wit.core.RetrofitCreator
import com.example.wit.data.api.ToiletAPI
import com.example.wit.data.api.response.ToiletResponse

class ToiletRemoteDataSourceImpl: ToiletRemoteDataSource {

    override suspend fun getToiletApiKey(): ToiletResponse {
        return RetrofitCreator.createToiletRetrofit(ToiletAPI::class.java).getToiletList()
    }
}