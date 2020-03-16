package com.example.news.presentation.home.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.news.R
import com.example.news.domain.adapters.AdapterRecView
import com.example.news.domain.use_cases.OnHolderClickListener
import com.example.news.presentation.home.BaseFragment
import kotlinx.android.synthetic.main.fragment_list.*


class NewsFragment : BaseFragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: AdapterRecView

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

        adapter = AdapterRecView(null, this, viewModel)
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
