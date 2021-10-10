package io.infinity.newsapp.viewmodels


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.infinity.newsapp.model.domain.ArticleDomainModel
import io.infinity.newsapp.model.network.asDomainModel
import io.infinity.newsapp.repository.NewsRepositoryInterface
import io.infinity.newsapp.repository.SearchHeadLinesPagingSource
import io.infinity.newsapp.services.networkservices.KNewsApiServices
import io.infinity.newsapp.services.networkservices.Resource
import io.infinity.newsapp.services.networkservices.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.sql.Struct
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val kNewsApiServices: KNewsApiServices
) :
    ViewModel() {

    val title: String = "Search"
    val showSelection: MutableLiveData<Boolean> = MutableLiveData(true)
    val _searchedNewsResults: MutableStateFlow<Resource<List<ArticleDomainModel>>> =
        MutableStateFlow(
            Resource.Loading()
        )
    val searchedNewsResults: StateFlow<Resource<List<ArticleDomainModel>>>
        get() = _searchedNewsResults
    var selectedCountry: String = "us"
    var selectedCategory: String = "health"
    var query = ""
    val searchPagingData: Flow<PagingData<ArticleDomainModel>>
        get() = Pager(
            config = PagingConfig(
                pageSize = 1,
                prefetchDistance = 3,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                SearchHeadLinesPagingSource(
                    kNewsApiServices,
                    country = selectedCountry,
                    category = selectedCategory,
                    query
                )
            }
        ).flow.cachedIn(viewModelScope)


}