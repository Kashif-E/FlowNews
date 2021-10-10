package io.infinity.newsapp

import android.app.Application
import androidx.appcompat.app.AlertDialog
import com.squareup.moshi.Moshi
import dagger.hilt.android.HiltAndroidApp
import io.infinity.newsapp.repository.NewsRepository
import io.infinity.newsapp.repository.NewsRepositoryInterface
import io.infinity.newsapp.services.networkservices.KNewsApiServices
import io.infinity.newsapp.services.networkservices.ResponseHandler
import io.infinity.newsapp.services.networkservices.createNewsAPPRetrofit

@HiltAndroidApp
class NewsAPP : Application() {


}