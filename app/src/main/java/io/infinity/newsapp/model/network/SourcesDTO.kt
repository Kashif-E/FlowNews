package io.infinity.newsapp.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.infinity.newsapp.model.domain.SourceDomainModel
import io.infinity.newsapp.model.domain.SourcesDomainModel


@JsonClass(generateAdapter = true)
data class NewsSourcesDTO(
    @Json(name = "sources")
    val sources: List<SourcesDTO>,
    @Json(name = "status")
    val status: String
)

@JsonClass(generateAdapter = true)
data class SourcesDTO(
    @Json(name = "category")
    val category: String,
    @Json(name = "country")
    val country: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "language")
    val language: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)

fun SourcesDTO.asDomainModel(): SourcesDomainModel = SourcesDomainModel(
    category = this.category,
    country = this.country,
    description = this.description,
    id = this.id,
    language = this.language,
    name = this.name,
    url = this.url

)

fun List<SourcesDTO>.asDomainModel(): List<SourcesDomainModel> = map {
    it.asDomainModel()
}