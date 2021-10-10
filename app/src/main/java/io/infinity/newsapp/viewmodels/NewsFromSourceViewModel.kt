package io.infinity.newsapp.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.infinity.newsapp.model.domain.ArticleDomainModel
import io.infinity.newsapp.repository.HeadLinesFromSourcePagingSource
import io.infinity.newsapp.services.networkservices.KNewsApiServices
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class NewsFromSourceViewModel @Inject constructor(private val  kNewsApiServices: KNewsApiServices, private val savedStateHandle: SavedStateHandle):
    ViewModel() {

    val source : String= savedStateHandle["sourceId"]!!
    val exploreTrendingNews: Flow<PagingData<ArticleDomainModel>>
    get() = Pager(
        config = PagingConfig(
            pageSize = 1,
            prefetchDistance = 3,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            HeadLinesFromSourcePagingSource(source=source,kNewsApiServices = kNewsApiServices)
        }
    ).flow.cachedIn(viewModelScope)

}