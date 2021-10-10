package io.infinity.newsapp.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.infinity.newsapp.R
import io.infinity.newsapp.databinding.ViewholderSourcesBinding
import io.infinity.newsapp.model.domain.SourcesDomainModel
import io.infinity.newsapp.ui.fragment.HeadLineItemClickListener
import io.infinity.newsapp.ui.fragment.OnSourceClicked


class SourcesAdapter(
    private val itemClicked: OnSourceClicked

) : RecyclerView.Adapter<SourcesAdapter.SourcesViewHolder>() {


    inner class SourcesViewHolder(private val itemViewBinding: ViewholderSourcesBinding) :
        RecyclerView.ViewHolder(
            itemViewBinding.root
        ) {

        fun bindView(sourceItem: SourcesDomainModel) {
            itemViewBinding.apply {
                source = sourceItem
            }
            itemViewBinding.root.setOnClickListener {
                itemClicked(sourceItem.id)
            }

        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<SourcesDomainModel>() {

        override fun areItemsTheSame(
            oldItem: SourcesDomainModel,
            newItem: SourcesDomainModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SourcesDomainModel,
            newItem: SourcesDomainModel
        ): Boolean {
            return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourcesViewHolder {
        return SourcesViewHolder(

            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.viewholder_sources, parent, false
            )

        )
    }


    override fun onBindViewHolder(holder: SourcesViewHolder, position: Int) {

        val sourceItem = differ.currentList[position]
        holder.bindView( sourceItem)
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}

