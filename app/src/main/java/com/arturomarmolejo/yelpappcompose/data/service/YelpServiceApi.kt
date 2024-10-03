package com.arturomarmolejo.yelpappcompose.data.service

import com.arturomarmolejo.yelpappcompose.data.model.YelpResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YelpServiceApi {

    @GET(SEARCH_PATH)
    suspend fun getAllBusinessesByLocation(
        @Header("Authorization") authHeader: String = AUTH_HEADER,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("term") term: String = "restaurants",
        @Query("attributes") attributes: String = "hot_and_new",
        @Query("sort_by") sortBy: String = "best_match",
        @Query("limit") limit: Int = 20
    ): Response<YelpResponse>

    companion object {
        //https://api.yelp.com/v3/businesses/search?latitude=33.87336&longitude=-84.469482&term=restaurants&attributes=hot_and_new&sort_by=best_match&limit=20
        const val BASE_URL = "https://api.yelp.com/v3/"
        const val SEARCH_PATH = "businesses/search"
        private const val API_KEY = "dLqWGBHu0noiq7_OX006gu0BDTVD4Mvbdwf2qYq_KDIVsYBxKMKnKQ4VcRthi6_2BH4Ttupf56KHLsgB-AC9k88pQ4K-9uUko9OYYSxw_finzW7_-w8E6fsuGsjyY3Yx"
        const val AUTH_HEADER = "Bearer $API_KEY"
    }
}