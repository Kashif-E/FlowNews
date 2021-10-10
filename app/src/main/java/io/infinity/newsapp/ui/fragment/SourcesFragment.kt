package io.infinity.newsapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.infinity.newsapp.R
import io.infinity.newsapp.adapters.SourcesAdapter
import io.infinity.newsapp.databinding.FragmentSourcesBinding
import io.infinity.newsapp.utils.Constants.QUERY_DEBOUNCE_TIME
import io.infinity.newsapp.utils.getQueryTextChangeStateFlow
import io.infinity.newsapp.viewmodels.SourcesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

typealias OnSourceClicked = (sourceId: String) -> Unit

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class SourcesFragment : Fragment(R.layout.fragment_sources) {

    private lateinit var binding: FragmentSourcesBinding
    private val viewModel: SourcesViewModel by viewModels()
    private lateinit var adapter: SourcesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentSourcesBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        setupAdapter()
        setupSearch()
        observeMutables()
    }

    private fun setupSearch() {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.searcView.getQueryTextChangeStateFlow()
                .debounce(QUERY_DEBOUNCE_TIME)
                .filter { query ->
                    if (query.isEmpty()) {
                        adapter.differ.submitList(viewModel.sources.value.data)
                        return@filter false
                    } else {
                        return@filter true
                    }
                }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    viewModel.sources.map {
                        it.data?.filter { source ->
                            source.name.contains(
                                query,
                                true
                            )
                        }
                    }
                        .catch {
                            Toast.makeText(requireContext(), "No item found", Toast.LENGTH_SHORT)
                                .show()
                        }
                }
                .flowOn(Dispatchers.Default)
                .collect { result ->
                    if (result.isNullOrEmpty()) {
                        Toast.makeText(requireContext(), "No item found", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        adapter.differ.submitList(result)
                    }

                }
        }
    }

    private fun setupAdapter() {
        adapter = SourcesAdapter { sourceId ->
            findNavController().navigate(
                R.id.action_sourcesFragment_to_newsFromSourceFragment,
                Bundle().apply {
                    putString("sourceId", sourceId)
                })
        }
        binding.recyclerView.adapter = adapter
    }

    private fun observeMutables() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sources.buffer().flowOn(Dispatchers.IO).map { it.data }
                    .collectLatest { headLines ->
                        adapter.differ.submitList(headLines)
                    }
            }
        }
    }


}