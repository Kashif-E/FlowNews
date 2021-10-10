package io.infinity.newsapp.repository

import io.infinity.newsapp.services.networkservices.KNewsApiServices
import io.infinity.newsapp.services.networkservices.ResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiServices: KNewsApiServices, private val responseHandler: ResponseHandler) : NewsRepositoryInterface {
    override suspend fun getTopHeadlines()= flow {
        val response = apiServices.getHeadLines(country = "us")

        emit(
            if (response.isSuccessful) {
                responseHandler
                    .handleSuccess(response.body(), response.code())
            } else {
                responseHandler
                    .handleException(response.message())
            }
        )

    }.flowOn(Dispatchers.IO).catch { e ->
        e.printStackTrace()
        responseHandler.handleException<String>(e.message!!)

    }
    override suspend fun getAllSources()= flow {
        val response = apiServices.getHeadLinesFromSources()

        emit(
            if (response.isSuccessful) {
                responseHandler
                    .handleSuccess(response.body()?.sources, response.code())
            } else {
                responseHandler
                    .handleException(response.message())
            }
        )

    }.flowOn(Dispatchers.IO).catch { e ->
        e.printStackTrace()
        responseHandler.handleException<String>(e.message!!)

    }

    override suspend fun getHeadlinesBasedOnCountry()= flow {
        val response = apiServices.getHeadLines(country = "us")

        emit(
            if (response.isSuccessful) {
                responseHandler
                    .handleSuccess(response.body(), response.code())
            } else {
                responseHandler
                    .handleException(response.message())
            }
        )

    }.flowOn(Dispatchers.IO).catch { e ->
        e.printStackTrace()
        responseHandler.handleException<String>(e.message!!)

    }
    override suspend fun getHeadlinesBasedOnCategory()= flow {
        val response = apiServices.getHeadLines(category = "health")

        emit(
            if (response.isSuccessful) {
                responseHandler
                    .handleSuccess(response.body(), response.code())
            } else {
                responseHandler
                    .handleException(response.message())
            }
        )

    }.flowOn(Dispatchers.IO).catch { e ->
        e.printStackTrace()
        responseHandler.handleException<String>(e.message!!)

    }
    override suspend fun getHeadLinesBasedOnCategoryAndCountry(
        query: String,
        selectedCategory: String,
        selectedCountry: String
    ) = flow {
        val response = apiServices.getHeadLines(country = selectedCountry,category =  selectedCategory, keyword = query)

        emit(
            if (response.isSuccessful) {
                responseHandler
                    .handleSuccess(response.body(), response.code())
            } else {
                responseHandler
                    .handleException(response.message())
            }
        )

    }.flowOn(Dispatchers.IO).catch { e ->
        e.printStackTrace()
        responseHandler.handleException<String>(e.message!!)

    }

    override suspend fun getHeadLinesFromSource(source: String) = flow {
        val response = apiServices.getHeadLines(source = source)

        emit(
            if (response.isSuccessful) {
                responseHandler
                    .handleSuccess(response.body(), response.code())
            } else {
                responseHandler
                    .handleException(response.message())
            }
        )

    }.flowOn(Dispatchers.IO).catch { e ->
        e.printStackTrace()
        responseHandler.handleException<String>(e.message!!)

    }

}