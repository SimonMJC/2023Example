package com.example.blackrabbit.data.repository.toilet

import com.example.blackrabbit.data.api.response.ToiletResponse
import com.example.blackrabbit.data.repository.toilet.remote.ToiletRemoteDataSource
import com.example.blackrabbit.data.repository.toilet.remote.ToiletRemoteDataSourceImpl

class ToiletRepositoryImpl : ToiletRepository {

    private val dataSource: ToiletRemoteDataSource = ToiletRemoteDataSourceImpl()
    override suspend fun getToiletList(): ToiletResponse = dataSource.getToiletApiKey()

    override suspend fun getLocations(): List<Pair<Long, Long>> = arrayListOf<Pair<Long, Long>>().apply {
        add((1000L to 1000L))
        add((2000L to 2000L))
        add((3000L to 3000L))
        add((4000L to 4000L))
    }
}