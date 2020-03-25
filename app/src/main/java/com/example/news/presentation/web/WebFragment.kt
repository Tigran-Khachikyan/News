package com.example.news.presentation.web

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.news.R
import com.example.news.data.api.hasNetwork
import com.example.news.presentation.KEY_ID
import kotlinx.android.synthetic.main.fragment_web.*

class WebFragment : Fragment() {

    private lateinit var viewModel: WebViewModel
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getString(KEY_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(WebViewModel::class.java)
        return inflater.inflate(R.layout.fragment_web, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView.isSoundEffectsEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressWeb?.apply { visibility = View.GONE }
            }
        }
        webView.settings.apply {
            javaScriptEnabled = true
            setAppCacheEnabled(true)
            setAppCachePath(requireContext().cacheDir.path)
            cacheMode =
                if (hasNetwork(requireContext())) WebSettings.LOAD_DEFAULT
                else WebSettings.LOAD_CACHE_ELSE_NETWORK
        }

        id?.let {
            viewModel.getArticleById(id!!).observe(viewLifecycleOwner, Observer {

                val dateTime =
                    requireContext().getString(R.string.published) + " " + it?.webPublicationDate
                tv_DateTime.text = dateTime
                webView.loadUrl(it?.webUrl)
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        webView?.destroy()
    }

}
