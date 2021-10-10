package io.infinity.newsapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.infinity.newsapp.R
import io.infinity.newsapp.adapters.ExploreNewsPagingAdapter
import io.infinity.newsapp.adapters.HomeFragmentHeadLinesAdapter
import io.infinity.newsapp.databinding.HomeFragmentBinding
import io.infinity.newsapp.model.domain.ArticleDomainModel
import io.infinity.newsapp.model.network.asDomainModel
import io.infinity.newsapp.viewmodels.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


typealias HeadLineItemClickListener = (headlineItem: ArticleDomainModel) -> Unit

typealias SaveHeadLineItemClickListener = (headlineItem: ArticleDomainModel) -> Unit

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var headLinesFromSourcesPagingAdapter: ExploreNewsPagingAdapter
    private lateinit var breakingNewsAdapter: HomeFragmentHeadLinesAdapter
    private lateinit var binding: HomeFragmentBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomeFragmentBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        setupAdapters()

        observeMutables()


    }

    private fun observeMutables() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.topHeadLinesBasedOnCountry.buffer().flowOn(Dispatchers.IO)
                    .map { it.data?.articles?.asDomainModel() }
                    .collectLatest { headLines ->

                        breakingNewsAdapter.differ.submitList(headLines)
                    }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.exploreTrendingNews.collectLatest { headLines ->

                    headLinesFromSourcesPagingAdapter.submitData(headLines)
                }
            }
        }

    }

    private fun setupAdapters() {
        breakingNewsAdapter = HomeFragmentHeadLinesAdapter({ articleClicked ->
            moveToArticleDetails(articleClicked)
        }, { saveArticleClicked ->

        })

        binding.rvHeadLines.adapter = breakingNewsAdapter
        headLinesFromSourcesPagingAdapter = ExploreNewsPagingAdapter { articleClicked ->
            moveToArticleDetails(articleClicked)
        }

        binding.rvHeadLinesFromSources.adapter = headLinesFromSourcesPagingAdapter

    }

    private fun moveToArticleDetails(article: ArticleDomainModel) {
        findNavController().navigate(
            R.id.action_homeFragment_to_articleDetailsFragment,
            Bundle().apply {
                putParcelable("article", article)
            })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTopHeadLines()
    }


}