package com.example.wit.data.repository.toilet

import com.example.wit.data.api.response.ToiletResponse

interface ToiletRepository {

    suspend fun getToiletList(): ToiletResponse

    suspend fun getLocations(): List<Pair<Long, Long>>
}