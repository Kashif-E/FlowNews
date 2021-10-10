package io.infinity.newsapp.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.infinity.newsapp.model.domain.ArticleDomainModel
import io.infinity.newsapp.model.domain.SourceDomainModel


@JsonClass(generateAdapter = true)
data class HeadlinesDTO(
    @Json(name = "articles")
    val articles: List<Article>,
    @Json(name = "status")
    val status: String,
    @Json(name = "totalResults")
    val totalResults: Int
)

@JsonClass(generateAdapter = true)
data class Source(
    @Json(name = "id")
    val id: Any? = "",
    @Json(name = "name")
    val name: String
)

@JsonClass(generateAdapter = true)
data class Article(
    @Json(name = "author")
    val author: String? = "Anonymous",
    @Json(name = "content")
    val content: String? = "",
    @Json(name = "description")
    val description: String? = "",
    @Json(name = "publishedAt")
    val publishedAt: String,
    @Json(name = "source")
    val source: Source,
    @Json(name = "title")
    val title: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "urlToImage")
    val urlToImage: String? = ""
)

fun List<Article>.asDomainModel(): List<ArticleDomainModel> {
    return map {
        ArticleDomainModel(
            author = it.author?: "Anonymous",
            content = it.content?:"",
            description = it.description?:"Click to see details",
            publishedAt = it.publishedAt,
            source = SourceDomainModel(
                id = it.source.id.toString(),
                name = it.source.name
            ),
            title = it.title,
            urlToImage = it.urlToImage?:"",
            url = it.url
        )
    }
}