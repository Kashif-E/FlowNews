package io.infinity.newsapp.repository

import io.infinity.newsapp.model.network.Article
import io.infinity.newsapp.model.network.HeadlinesDTO
import io.infinity.newsapp.model.network.Source
import io.infinity.newsapp.services.networkservices.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeNewsRepository : NewsRepositoryInterface {
    override suspend fun getTopHeadlines(): Flow<Resource<HeadlinesDTO>> {
        return flowOf<Resource<HeadlinesDTO>>(
            Resource.Success(
                HeadlinesDTO(
                    status = "",
                    totalResults = 3,
                    articles = listOf(
                        Article(
                            author = "",
                            content = "",
                            description = "",
                            publishedAt = "",
                            source = Source(
                                id = "s",
                                name = ""
                            ),
                            title = "",
                            url = "",
                            urlToImage = ""

                        )
                    )
                ), 200
            )
        )
    }

    override suspend fun getHeadlinesBasedOnCountry(): Flow<Resource<HeadlinesDTO>> {
        return flowOf<Resource<HeadlinesDTO>>(
            Resource.Success(
                HeadlinesDTO(
                    status = "",
                    totalResults = 3,
                    articles = listOf(
                        Article(
                            author = "",
                            content = "",
                            description = "",
                            publishedAt = "",
                            source = Source(
                                id = "s",
                                name = ""
                            ),
                            title = "",
                            url = "",
                            urlToImage = ""

                        )
                    )
                ), 200
            )
        )
    }


    override suspend fun getHeadlinesBasedOnCategory(): Flow<Resource<HeadlinesDTO>> {
        return flowOf<Resource<HeadlinesDTO>>(
            Resource.Success(
                HeadlinesDTO(
                    status = "",
                    totalResults = 3,
                    articles = listOf(
                        Article(
                            author = "",
                            content = "",
                            description = "",
                            publishedAt = "",
                            source = Source(
                                id = "s",
                                name = ""
                            ),
                            title = "",
                            url = "",
                            urlToImage = ""

                        )
                    )
                ), 200
            )
        )
    }

    override suspend fun getHeadLinesBasedOnCategoryAndCountry(
        query: String,
        selectedCategory: String,
        selectedCountry: String
    ): Flow<Resource<HeadlinesDTO>> {
        return flowOf<Resource<HeadlinesDTO>>(
            Resource.Success(
                HeadlinesDTO(
                    status = "",
                    totalResults = 3,
                    articles = listOf(
                        Article(
                            author = "",
                            content = "",
                            description = "",
                            publishedAt = "",
                            source = Source(
                                id = "s",
                                name = ""
                            ),
                            title = "",
                            url = "",
                            urlToImage = ""

                        )
                    )
                ), 200
            )
        )
    }


    override suspend fun getHeadLinesFromSource(source: String): Flow<Resource<HeadlinesDTO>> {
        return flowOf<Resource<HeadlinesDTO>>(
            Resource.Success(
                HeadlinesDTO(
                    status = "",
                    totalResults = 3,
                    articles = listOf(
                        Article(
                            author = "",
                            content = "",
                            description = "",
                            publishedAt = "",
                            source = Source(
                                id = "s",
                                name = ""
                            ),
                            title = "",
                            url = "",
                            urlToImage = ""

                        )
                    )
                ), 200
            )
        )
    }
}
