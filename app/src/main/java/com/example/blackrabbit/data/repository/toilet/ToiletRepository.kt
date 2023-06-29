package com.example.blackrabbit.data.repository.toilet

import com.example.blackrabbit.data.api.response.ToiletResponse

interface ToiletRepository {

    suspend fun getToiletList(): ToiletResponse

    suspend fun getLocations(): List<Pair<Long, Long>>
}