package com.example.blackrabbit.data.repository.toilet.remote

import com.example.blackrabbit.core.RetrofitCreator
import com.example.blackrabbit.data.api.ToiletAPI
import com.example.blackrabbit.data.api.response.ToiletResponse

class ToiletRemoteDataSourceImpl: ToiletRemoteDataSource {

    override suspend fun getToiletApiKey(): ToiletResponse {
        return RetrofitCreator.createToiletRetrofit(ToiletAPI::class.java).getToiletList()
    }
}