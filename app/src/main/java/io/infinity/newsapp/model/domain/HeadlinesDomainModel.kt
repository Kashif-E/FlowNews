package io.infinity.newsapp.model.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class HeadlinesDomainModel(

    val articles: List<ArticleDomainModel>,

    val status: String,

    val totalResults: Int
): Parcelable

@Parcelize
data class SourceDomainModel(

    val id: String,

    val name: String
): Parcelable

@Parcelize
data class ArticleDomainModel(

    val author: String,

    val content: String,

    val description: String,

    val publishedAt: String,

    val source: SourceDomainModel,

    val title: String,

    val url: String,

    val urlToImage: String
): Parcelable