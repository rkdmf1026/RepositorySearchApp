package com.example.repositorysearchapp.network

import com.example.repositorysearchapp.network.dto.ItemListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GitApi {
    @GET("/search/repositories")
    suspend fun getRepositoryList(
        @Query("q") q: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): ItemListResponse
}
