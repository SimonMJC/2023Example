package com.example.wit.data.repository.toilet.remote

import com.example.wit.data.api.response.ToiletResponse

interface ToiletRemoteDataSource {

    suspend fun getToiletApiKey(): ToiletResponse
}