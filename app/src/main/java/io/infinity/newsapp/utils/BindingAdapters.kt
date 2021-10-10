package io.infinity.newsapp.utils

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

@BindingAdapter("loadImageFromUrl")
fun loadImageFromUrl(imageView: ImageView, url: String?) {
    url?.let {
        Glide
            .with(imageView.context)
            .load(url)
            .fitCenter().diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(imageView)
    }

}
@BindingAdapter("setDateAndTime")
fun setDateAndTime(view: TextView, dateTime: String?) {
    dateTime?.let {
        view.text = formatDate(dateTime)
    }

}

@BindingAdapter("loadArticleInWeb")
fun loadInWebView(webView: WebView, url: String?){
    url?.let {
        webView.webViewClient = WebViewClient()
        Log.e("url", url)
        webView.loadUrl(url)
    }
}