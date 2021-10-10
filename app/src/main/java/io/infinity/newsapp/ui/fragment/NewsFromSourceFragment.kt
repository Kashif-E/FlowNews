package io.infinity.newsapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.infinity.newsapp.R
import io.infinity.newsapp.adapters.ExploreNewsPagingAdapter
import io.infinity.newsapp.databinding.FragmentNewsFromSourcesBinding
import io.infinity.newsapp.model.domain.ArticleDomainModel
import io.infinity.newsapp.viewmodels.NewsFromSourceViewModel
import kotlinx.android.synthetic.main.toolbar_generic.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFromSourceFragment : Fragment(R.layout.fragment_news_from_sources) {

    private val viewModel: NewsFromSourceViewModel by viewModels()
    private lateinit var binding: FragmentNewsFromSourcesBinding

    private lateinit var adapter: ExploreNewsPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNewsFromSourcesBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setUpadapter()
        observeMutables()
    }

    private fun setUpadapter() {
        binding.toolbar.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        adapter = ExploreNewsPagingAdapter { articleClicked ->
            moveToArticleDetails(articleClicked)
        }
        binding.recyclerView.adapter = adapter
    }

    private fun moveToArticleDetails(articleClicked: ArticleDomainModel) {
        findNavController().navigate(
            R.id.action_newsFromSourceFragment_to_articleDetailsFragment2,
            Bundle().apply {
                putParcelable("article", articleClicked)
            })
    }

    private fun observeMutables() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.exploreTrendingNews.buffer().flowOn(Dispatchers.IO)
                    .collect { headlines ->
                        adapter.submitData(headlines)
                    }
            }
        }
    }




}