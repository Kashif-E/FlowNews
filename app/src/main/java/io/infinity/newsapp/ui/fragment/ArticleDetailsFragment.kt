package io.infinity.newsapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import io.infinity.newsapp.R
import io.infinity.newsapp.databinding.FragmentArticleDetailsBinding
import io.infinity.newsapp.viewmodels.ArticleDetailsViewModel
import kotlinx.android.synthetic.main.toolbar_generic.view.*
import kotlinx.coroutines.flow.collect


class ArticleDetailsFragment : Fragment(R.layout.fragment_article_details) {

    private lateinit var binding : FragmentArticleDetailsBinding
    private val articleDetailsViewModel: ArticleDetailsViewModel by viewModels()
    private val args: ArticleDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentArticleDetailsBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.article = args.article
        binding.viewModel = articleDetailsViewModel


        binding.toolbar.ivBack.setOnClickListener {
          findNavController().navigateUp()

        }



        setSystemUIBackListener()

    }
    private fun setSystemUIBackListener() {
        //Add back press callback to save package details on back pressed
        val callback = object : OnBackPressedCallback(
            true
            /** true means that the callback is enabled */
        ) {
            override fun handleOnBackPressed() {
                if (articleDetailsViewModel.isWebViewShowing.value!!){
                    articleDetailsViewModel.changeWebViewState()
                }else{
                    findNavController().navigateUp()
                }
            }
        }
        /*Back pressed callback is added to onBackPressedDispatcher to handle Activity's back pressed
        * Point to note here: The addCallback function takes viewlifecycleOwner so that as soon as fragment is destroyed, the callback is removed and
        * avoid memory leaks*/
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}