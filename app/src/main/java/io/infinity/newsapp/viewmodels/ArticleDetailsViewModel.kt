package io.infinity.newsapp.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ArticleDetailsViewModel : ViewModel() {
    val title= "Sources"
    private val _isWebViewShowing = MutableLiveData(false)
    val isWebViewShowing: LiveData<Boolean>
        get() = _isWebViewShowing

    fun onReadMoreClicked() {

        _isWebViewShowing.postValue(true)


    }

    fun changeWebViewState() {
        _isWebViewShowing.postValue(false)

    }

}