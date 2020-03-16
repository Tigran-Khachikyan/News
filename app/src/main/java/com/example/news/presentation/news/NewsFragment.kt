package com.example.news.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.news.R
import com.example.news.presentation.RecyclerViewAdapter
import com.example.news.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_list.*

class NewsFragment : BaseFragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.visibility = View.GONE

        adapter = RecyclerViewAdapter(
            null,
            this,
            viewModel
        )
        recyclerArticles.setHasFixedSize(true)
        recyclerArticles.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerArticles.adapter = adapter

        viewModel.news.observe(viewLifecycleOwner, Observer {

            progress.visibility = View.GONE
            adapter.articles = it
            adapter.notifyDataSetChanged()

        })
    }

}
