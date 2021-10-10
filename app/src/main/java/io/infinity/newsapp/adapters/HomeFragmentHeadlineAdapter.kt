package io.infinity.newsapp.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.infinity.newsapp.R
import io.infinity.newsapp.databinding.ViewholderHeadlineNewsBinding
import io.infinity.newsapp.model.domain.ArticleDomainModel
import io.infinity.newsapp.ui.fragment.HeadLineItemClickListener
import io.infinity.newsapp.ui.fragment.SaveHeadLineItemClickListener


class HomeFragmentHeadLinesAdapter(
    private val itemClicked:  HeadLineItemClickListener,
    private val saveItemClicked: SaveHeadLineItemClickListener

) : RecyclerView.Adapter<HomeFragmentHeadLinesAdapter.ArticleDomainModelViewHolder>() {


    inner class ArticleDomainModelViewHolder(private val itemViewBinding: ViewholderHeadlineNewsBinding) :
        RecyclerView.ViewHolder(
            itemViewBinding.root
        ) {

        fun bindView(articleItem: ArticleDomainModel) {
            itemViewBinding.apply {
                article = articleItem
            }
            itemViewBinding.root.setOnClickListener {
                itemClicked(articleItem)
            }
            itemViewBinding.btnSave.setOnClickListener {
                saveItemClicked(articleItem)
            }
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<ArticleDomainModel>() {

        override fun areItemsTheSame(
            oldItem: ArticleDomainModel,
            newItem: ArticleDomainModel
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: ArticleDomainModel,
            newItem: ArticleDomainModel
        ): Boolean {
            return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleDomainModelViewHolder {
        return ArticleDomainModelViewHolder(

            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.viewholder_headline_news, parent, false
            )

        )
    }


    override fun onBindViewHolder(holder: ArticleDomainModelViewHolder, position: Int) {

        val articleItem = differ.currentList[position]
        holder.bindView( articleItem)
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}

