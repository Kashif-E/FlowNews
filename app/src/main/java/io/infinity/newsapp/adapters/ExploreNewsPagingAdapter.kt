package io.infinity.newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.infinity.newsapp.R
import io.infinity.newsapp.databinding.ViewholderNewsItemBinding
import io.infinity.newsapp.model.domain.ArticleDomainModel
import io.infinity.newsapp.ui.fragment.HeadLineItemClickListener


val differCallBack = object : DiffUtil.ItemCallback<ArticleDomainModel>() {

    override fun areItemsTheSame(
        oldItem: ArticleDomainModel,
        newItem:ArticleDomainModel
    ): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(
        oldItem:ArticleDomainModel,
        newItem:ArticleDomainModel
    ): Boolean {
        return oldItem == newItem
    }


}

class ExploreNewsPagingAdapter(
    private val itemClicked: HeadLineItemClickListener,
  
) : PagingDataAdapter<ArticleDomainModel, ExploreNewsPagingAdapter.CatalogViewHolder>(differCallBack) {


    inner class CatalogViewHolder(private val itemViewBinding: ViewholderNewsItemBinding) :
        RecyclerView.ViewHolder(
            itemViewBinding.root
        ) {

        fun bindView(ArticleItem:ArticleDomainModel) {
            itemViewBinding.apply {
                article =ArticleItem
            }

            itemViewBinding.root.setOnClickListener {
                itemClicked(ArticleItem)
            }
           
        }
    }


    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        return CatalogViewHolder(

            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.viewholder_news_item, parent, false
            )

        )
    }


    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {

        getItem(position)?.let {
            holder.bindView(ArticleItem = it)
        }

    }


}

