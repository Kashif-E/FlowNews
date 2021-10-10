package io.infinity.newsapp.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.infinity.newsapp.NewsAPP
import io.infinity.newsapp.repository.NewsRepository
import io.infinity.newsapp.repository.NewsRepositoryInterface
import io.infinity.newsapp.services.networkservices.KNewsApiServices
import io.infinity.newsapp.services.networkservices.ResponseHandler
import io.infinity.newsapp.services.networkservices.createNewsAPPRetrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {


/*    private var dataStoreManager: DataStoreManager? = null
    fun getDatastoreManager(): DataStoreManager {
        synchronized(NewsAPP::class.java) {
            if (dataStoreManager == null)
                dataStoreManager =
                    DataStoreManager(getInstance().baseContext)
        }
        return dataStoreManager!!
    }*/


    private var responseHandler: ResponseHandler? = null

    @Provides
    @Singleton
    fun getResponseHandler(): ResponseHandler {
        synchronized(NewsAPP::class.java) {
            if (responseHandler == null)
                responseHandler = ResponseHandler()
        }
        return responseHandler!!

    }

    private var moshi: Moshi? = null

    @Provides
    @Singleton
    fun getMoshi(): Moshi {
        synchronized(NewsAPP::class.java) {
            if (moshi == null)
                moshi = Moshi.Builder().build()
        }
        return moshi!!

    }

    private var KNewsApiServices: KNewsApiServices? = null

    @Provides
    @Singleton
    fun getNewsAPPApiService(): KNewsApiServices {
        synchronized(NewsAPP::class.java) {
            if (KNewsApiServices == null)
                KNewsApiServices = createNewsAPPRetrofit(getMoshi())
        }
        return KNewsApiServices!!
    }


    private var newsRepositoryInterface: NewsRepository? = null

    @Provides
    @Singleton
    fun getNewsRepository(): NewsRepositoryInterface {
        synchronized(NewsAPP::class.java) {
            if (newsRepositoryInterface == null) {
                newsRepositoryInterface = NewsRepository(getNewsAPPApiService(), getResponseHandler())

            }

        }
        return newsRepositoryInterface!!

    }
}