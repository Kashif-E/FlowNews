package io.infinity.newsapp.services.networkservices

import io.infinity.newsapp.model.network.HeadlinesDTO
import io.infinity.newsapp.model.network.NewsSourcesDTO
import io.infinity.newsapp.utils.Constants.HEADLINE_PAGESIZE

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KNewsApiServices {

    @GET(HEADLINES)
     suspend fun getHeadLines(
        @Query("pagesize")pageSize: Int?= HEADLINE_PAGESIZE,
        @Query("page") page:Int?= null,
        @Query("country") country: String?=null,
        @Query("sources") source: String?=null,
        @Query("category")category: String?= null,
        @Query("q")keyword: String?=null
    ): Response<HeadlinesDTO>

    @GET(HEADLINES_FROM_SOURCES)
    suspend fun getHeadLinesFromSources(
    ): Response<NewsSourcesDTO>



}