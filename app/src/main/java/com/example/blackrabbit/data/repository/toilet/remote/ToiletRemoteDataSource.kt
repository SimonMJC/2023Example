package com.example.blackrabbit.data.repository.toilet.remote

import com.example.blackrabbit.data.api.response.ToiletResponse

interface ToiletRemoteDataSource {

    suspend fun getToiletApiKey(): ToiletResponse
}