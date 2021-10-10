package io.infinity.newsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.infinity.newsapp.model.domain.ArticleDomainModel
import io.infinity.newsapp.model.network.HeadlinesDTO
import io.infinity.newsapp.repository.NewsRepositoryInterface
import io.infinity.newsapp.repository.TopHeadLinesPagingSources
import io.infinity.newsapp.services.networkservices.KNewsApiServices
import io.infinity.newsapp.services.networkservices.Resource
import io.infinity.newsapp.services.networkservices.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val newsRepository: NewsRepositoryInterface, private val kNewsApiServices: KNewsApiServices) :
    ViewModel() {

    val title = "Headlines"
    private val _topHeadLinesBasedOnCountry: MutableStateFlow<Resource<HeadlinesDTO>> =
        MutableStateFlow(Resource.Loading())
    val topHeadLinesBasedOnCountry: StateFlow<Resource<HeadlinesDTO>>
        get() = _topHeadLinesBasedOnCountry

    val exploreTrendingNews: Flow<PagingData<ArticleDomainModel>>
        get() = Pager(
        config = PagingConfig(
            pageSize = 1,
            prefetchDistance = 5,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
           TopHeadLinesPagingSources(kNewsApiServices)
        }
    ).flow.cachedIn(viewModelScope)



    fun getTopHeadLines() {

        viewModelScope.launch{
            newsRepository.getTopHeadlines().flowOn(Dispatchers.IO).collect { response ->
                withContext(Dispatchers.Main) {
                    when (response.status) {

                        Status.SUCCESS -> {
                            _topHeadLinesBasedOnCountry.emit(
                                Resource.Success(
                                    data = response.data,
                                    responseCode = response.responseCode!!
                                )
                            )
                        }
                        Status.ERROR -> {
                            _topHeadLinesBasedOnCountry.emit(
                                Resource.Error(
                                    response.message!!,
                                    responseCode = response.responseCode!!
                                )
                            )
                        }

                        Status.NO_DATA_FOUND -> {
                            _topHeadLinesBasedOnCountry.emit(
                                Resource.Error(
                                    response.message!!,
                                    responseCode = response.responseCode!!
                                )
                            )
                        }
                        Status.NO_INTERNET_CONNECTION -> {
                            _topHeadLinesBasedOnCountry.emit(
                                Resource.Error(
                                    response.message!!,
                                    responseCode = response.responseCode!!
                                )
                            )
                        }
                        else -> {
                            _topHeadLinesBasedOnCountry.emit(
                                Resource.Error(
                                    "Something went wrong",
                                    responseCode = response.responseCode!!
                                )
                            )
                        }

                    }
                }
            }
        }
    }

}