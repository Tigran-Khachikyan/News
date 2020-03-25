package com.example.news.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.domain.models.ModelApi
import com.example.news.domain.models.ModelDb
import com.example.news.domain.use_cases.OnAdapterFeaturesChangeListener
import com.example.news.presentation.KEY_ID
import com.example.news.presentation.RecyclerViewAdapter
import com.example.news.presentation.web.WebFragment

abstract class BaseFragment : Fragment() {

    protected lateinit var viewModel: BaseViewModel
    private lateinit var onFavoriteChangeListener: OnAdapterFeaturesChangeListener
    protected lateinit var adapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        onFavoriteChangeListener = object : OnAdapterFeaturesChangeListener {
            override fun openWebFragment(id: String) {

                val webFragment = WebFragment()
                webFragment.arguments = Bundle().apply { putString(KEY_ID, id) }

                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.frame, webFragment).addToBackStack(null).commit()
            }

            override fun addIntoFavourites(article: ModelDb) {
                viewModel.addIntoFavourites(article)
            }

            override fun removeFromFavourites(id: String) {
                viewModel.removeFromFavourites(id)
            }
        }

        adapter = RecyclerViewAdapter(null, onFavoriteChangeListener)
        val recyclerArticles = view.findViewById<RecyclerView>(R.id.recyclerArticles)
        recyclerArticles.setHasFixedSize(true)
        recyclerArticles.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerArticles.adapter = adapter
        return view
    }
}