package io.infinity.newsapp.viewmodels

import androidx.lifecycle.ViewModel
import io.infinity.newsapp.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
@ExperimentalCoroutinesApi
class HomeViewModelTest : ViewModel() {
 /*   @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()*/


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeViewModel
}