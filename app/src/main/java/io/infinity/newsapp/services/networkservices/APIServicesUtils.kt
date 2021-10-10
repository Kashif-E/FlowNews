package io.infinity.newsapp.services.networkservices

import android.util.Log
import com.squareup.moshi.Moshi
import io.infinity.newsapp.NewsAPP
import io.infinity.newsapp.utils.Constants
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject


private fun makeHttpClient(


): OkHttpClient {
    val okBuilder = OkHttpClient.Builder()

    okBuilder.connectTimeout(60, TimeUnit.SECONDS)
    okBuilder.readTimeout(60, TimeUnit.SECONDS)
    okBuilder.addInterceptor(headersInterceptor())
    okBuilder.addInterceptor(loggingInterceptor())
    okBuilder.addInterceptor(HeaderInterceptor())
    return okBuilder.build()
}

private fun headersInterceptor() =
    Interceptor { chain ->



        chain.proceed(
            chain.request().newBuilder()
                .addHeader(
                    "x-api-key", Constants.API_KEY
                )
                .build()
        )

    }


private fun loggingInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
    // level = HttpLoggingInterceptor.Level.HEADERS
}

private fun HeaderInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.HEADERS
}



fun createNewsAPPRetrofit (moshi: Moshi): KNewsApiServices {
    val packageXRetrofit = Retrofit.Builder()
        .addConverterFactory(
            MoshiConverterFactory.create(moshi)
        )
        .addConverterFactory(
            ScalarsConverterFactory.create()
        )
        .baseUrl(
           Constants.BASE_URL

        )
        .client(makeHttpClient())
        .build()
    return packageXRetrofit.create(
       KNewsApiServices::class.java
    )

}