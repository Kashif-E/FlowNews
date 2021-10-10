package io.infinity.newsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.infinity.newsapp.model.domain.SourcesDomainModel
import io.infinity.newsapp.model.network.asDomainModel
import io.infinity.newsapp.repository.NewsRepositoryInterface
import io.infinity.newsapp.services.networkservices.Resource
import io.infinity.newsapp.services.networkservices.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SourcesViewModel @Inject constructor  (private val newsRepository: NewsRepositoryInterface,) :
    ViewModel() {
    val title="Sources"
    private val _sources: MutableStateFlow<Resource<List<SourcesDomainModel>>> =
        MutableStateFlow(Resource.Loading())
    val sources: StateFlow<Resource<List<SourcesDomainModel>>>
        get() = _sources

    init {
        getAlSources()
    }
    fun getAlSources() {

        viewModelScope.launch {
            newsRepository.getAllSources().flowOn(Dispatchers.IO).collect { response ->


                when (response.status) {

                    Status.SUCCESS -> {
                        _sources.emit(
                            Resource.Success(
                                data = response.data?.asDomainModel(),
                                responseCode = response.responseCode!!
                            )
                        )
                    }
                    Status.ERROR -> {
                        _sources.emit(
                            Resource.Error(
                                response.message!!,
                                responseCode = response.responseCode!!
                            )
                        )
                    }

                    Status.NO_DATA_FOUND -> {
                        _sources.emit(
                            Resource.Error(
                                response.message!!,
                                responseCode = response.responseCode!!
                            )
                        )
                    }
                    Status.NO_INTERNET_CONNECTION -> {
                        _sources.emit(
                            Resource.Error(
                                response.message!!,
                                responseCode = response.responseCode!!
                            )
                        )
                    }
                    else -> {
                        _sources.emit(
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