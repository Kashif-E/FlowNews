package io.infinity.newsapp.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.infinity.newsapp.model.domain.ArticleDomainModel
import io.infinity.newsapp.model.network.asDomainModel
import io.infinity.newsapp.services.networkservices.KNewsApiServices
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TopHeadLinesPagingSources @Inject constructor(private val kNewsApiServices: KNewsApiServices) : PagingSource<Int, ArticleDomainModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,ArticleDomainModel> {

        val position = params.key?.let {
            if (params.key ==0){
                return@let 1
            }else{
                return@let params.key
            }
        }?: 1
        return try {

            Log.e("paga", "paaa")
            val response =
                kNewsApiServices.getHeadLines(pageSize = 10, page = position, country = "us")


            LoadResult.Page(
                data = response.body()?.articles?.asDomainModel() ?: emptyList(),
                prevKey = if (params.loadSize == 1) null else position - 1,
                nextKey = if (response.body()?.articles.isNullOrEmpty()) null else position + 1

            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, ArticleDomainModel>): Int? {
        return null
    }
}