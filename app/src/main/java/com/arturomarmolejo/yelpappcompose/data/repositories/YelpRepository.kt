package com.arturomarmolejo.yelpappcompose.data.repositories

import com.arturomarmolejo.yelpappcompose.core.UIState
import com.arturomarmolejo.yelpappcompose.data.model.Businesse
import com.arturomarmolejo.yelpappcompose.data.model.YelpResponse
import com.arturomarmolejo.yelpappcompose.data.service.YelpServiceApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface YelpRepository {
    suspend fun getAllBusinessesByLocation(latitude: Double, longitude: Double): Flow<UIState<YelpResponse>>
    suspend fun getBusinessById(id: String): Flow<UIState<Businesse?>>
}

class YelpRepositoryImpl @Inject constructor(
    private val yelpServiceApi: YelpServiceApi,
    private val coroutineDispatcher: CoroutineDispatcher
): YelpRepository {

    override suspend fun getAllBusinessesByLocation(
        latitude: Double,
        longitude: Double
    ): Flow<UIState<YelpResponse>> = flow {
        emit(UIState.LOADING)
        try {
            val response = yelpServiceApi.getAllBusinessesByLocation(latitude = latitude,
                longitude = longitude)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(UIState.SUCCESS(it))
                } ?: throw Exception("Response body is null")
            } else {
                throw Exception(response.errorBody().toString())
            }
        } catch (e: Exception) {
            emit(UIState.ERROR(e))
        }
    }.flowOn(coroutineDispatcher)

    override suspend fun getBusinessById(id: String): Flow<UIState<Businesse?>> = flow {
        emit(UIState.LOADING)
        try {
            val response = yelpServiceApi.getBusinessById(id = id)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(UIState.SUCCESS(it))
                } ?: throw Exception("Response body is null")
            } else {
                throw(Exception(response.errorBody().toString()))
            }
        } catch (e: Exception) {
            emit(UIState.ERROR(e))
        }
    }.flowOn(coroutineDispatcher)
}