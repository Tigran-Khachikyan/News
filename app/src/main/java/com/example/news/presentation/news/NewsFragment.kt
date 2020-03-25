package com.example.news.presentation.news

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class NewsFragment : BaseFragment(), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerArticles.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layManager = recyclerView.layoutManager as LinearLayoutManager
                val bottomPosition = layManager.findLastCompletelyVisibleItemPosition()
                val itemCount = recyclerView.adapter?.itemCount
                itemCount?.let {
                    if (it != 0 && it - 1 == bottomPosition) {
                        var pageNumber = it / 10
                        if (pageNumber < 5)
                            (viewModel as NewsViewModel).callNextPageNews(++pageNumber)
                    }
                }
            }
        })

        tvWarning.text = requireContext().getString(R.string.noArticleFound)
        fab.visibility = View.GONE

        (viewModel as NewsViewModel).getNews().observe(viewLifecycleOwner, Observer {
            progress.visibility = View.VISIBLE

            Log.d("hhhs", "OBSERVER: ${it?.map { m -> m.isFavourite }}")

            if (it != null && it.isNotEmpty()) {
                progress.visibility = View.GONE
                tvWarning.visibility = View.GONE
                adapter.articles = it
                adapter.notifyDataSetChanged()
                tvWarning.visibility = View.GONE
            }
        })

        launch {
            delay(2000)
            if (adapter.articles.isNullOrEmpty())
                tvWarning.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (viewModel as NewsViewModel).cancel()
        job.cancel()
    }

}
