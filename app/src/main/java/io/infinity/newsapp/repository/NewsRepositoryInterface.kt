package io.infinity.newsapp.repository


import io.infinity.newsapp.model.network.HeadlinesDTO
import io.infinity.newsapp.model.network.SourcesDTO
import io.infinity.newsapp.services.networkservices.Resource
import kotlinx.coroutines.flow.Flow


interface NewsRepositoryInterface {
     suspend fun getTopHeadlines(): Flow<Resource<HeadlinesDTO>>

     suspend fun getHeadlinesBasedOnCountry(): Flow<Resource<HeadlinesDTO>>

     suspend fun getHeadlinesBasedOnCategory(): Flow<Resource<HeadlinesDTO>>

     suspend fun getHeadLinesBasedOnCategoryAndCountry(
          query: String,
          selectedCategory: String,
          selectedCountry: String
     ): Flow<Resource<HeadlinesDTO>>

     suspend fun getHeadLinesFromSource(source: String): Flow<Resource<HeadlinesDTO>>

     suspend fun  getAllSources():Flow<Resource<List<SourcesDTO>>>
}