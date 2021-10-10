package io.infinity.newsapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import dagger.hilt.android.AndroidEntryPoint
import io.infinity.newsapp.R
import io.infinity.newsapp.adapters.ExploreNewsPagingAdapter
import io.infinity.newsapp.databinding.FragmentSearchBinding
import io.infinity.newsapp.model.domain.ArticleDomainModel
import io.infinity.newsapp.utils.Constants
import io.infinity.newsapp.utils.getChipChangedStateFlow
import io.infinity.newsapp.utils.getQueryTextChangeStateFlow
import io.infinity.newsapp.viewmodels.SearchViewModel
import kotlinx.android.synthetic.main.toolbar_generic.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: ExploreNewsPagingAdapter
    private lateinit var binding: FragmentSearchBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSearchBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setUpadapter()
        setupSearch()
        observeMutables()


    }

    private fun moveToArticleDetails(articleClicked: ArticleDomainModel) {
        findNavController().navigate(
            R.id.action_searchFragment_to_articleDetailsFragment3,
            Bundle().apply {
                putParcelable("article", articleClicked)
            })
    }

    private fun setUpadapter() {

        adapter = ExploreNewsPagingAdapter { articleClicked ->
            moveToArticleDetails(articleClicked)
        }
        binding.rvNews.adapter = adapter
    }

    private fun setupSearch() {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.searchView.getQueryTextChangeStateFlow()
                .debounce(Constants.QUERY_DEBOUNCE_TIME)
                .filter { query ->
                    if (query.isEmpty()) {
                        adapter.submitData(PagingData.empty())
                        viewModel.showSelection.postValue( true)
                        return@filter false
                    } else {
                        viewModel.query =query
                        return@filter true
                    }
                }
                .distinctUntilChanged()
                .flatMapLatest {

                    adapter.refresh()
                    delay(1000)
                    viewModel.searchPagingData

                }.flowOn(Dispatchers.IO).collect {

                    viewModel.showSelection.postValue(false)
                        adapter.submitData(it)

                }
        }
    }

    private fun observeMutables() {



        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                binding.countryChipGroup.getChipChangedStateFlow().collect {
                    viewModel.selectedCountry = it


                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                binding.chipGroupCategory.getChipChangedStateFlow().collect {
                    viewModel.selectedCategory = it


                }
            }
        }
    }
        }
