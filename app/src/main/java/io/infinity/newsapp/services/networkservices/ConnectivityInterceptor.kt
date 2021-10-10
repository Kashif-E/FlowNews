package io.infinity.newsapp.services.networkservices


import android.content.Context
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException
import javax.inject.Inject


open class ConnectivityInterceptor @Inject constructor(private val context: Context) : Interceptor {

    val state =
        NetworkStatusTracker(context = context ).networkStatus
            .map(
                onAvailable = { MyState.Fetched },
                onUnavailable = { MyState.Error }
            )
            .asLiveData(Dispatchers.IO)

    private val isConnected: Boolean
        get() {
            return when(state.value){
                is MyState.Fetched ->{
                    return true
                }
                is MyState.Error->{
                    return false
                }
                else -> false
            }
        }



    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (!isConnected) {
            throw NoNetworkException()
        }
        return chain.proceed(originalRequest)
    }

    class NoNetworkException internal constructor() :
        IOException(Status.NO_INTERNET_CONNECTION.toString())
}