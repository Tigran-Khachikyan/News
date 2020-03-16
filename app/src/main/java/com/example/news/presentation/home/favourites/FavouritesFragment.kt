package com.example.news.presentation.home.favourites

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.domain.adapters.AdapterRecView
import com.example.news.presentation.home.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_list.*

class FavouritesFragment : BaseFragment() {

    companion object {
        fun newInstance() = FavouritesFragment()
    }

    private lateinit var viewModel: FavouritesViewModel
    private lateinit var adapter: AdapterRecView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(FavouritesViewModel::class.java)
        return inflater.inflate(R.layout.fragment_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener { view ->
            showDialog(requireContext()) {
                Snackbar.make(
                        view,
                        requireContext().getString(R.string.successfullyRemoved),
                        Snackbar.LENGTH_LONG
                    )
                    .setAction("Action", null).show()
            }
        }

        adapter = AdapterRecView(null, this, viewModel)
        recyclerArticles.setHasFixedSize(true)
        recyclerArticles.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL, false
        )
        recyclerArticles.adapter = adapter

        viewModel.favouriteNews.observe(viewLifecycleOwner, Observer {

            fab.visibility = if(it.isEmpty()) View.GONE else View.VISIBLE

            progress.visibility = View.GONE
            adapter.articles = it
            adapter.notifyDataSetChanged()

        })
    }

    private fun showDialog(context: Context, func: () -> Unit) {

        val dialBuilder = AlertDialog.Builder(context)
           dialBuilder.setTitle(R.string.warning)
           dialBuilder.setIcon(R.drawable.ic_alert)
           dialBuilder.setMessage(R.string.warningText)

        //click SAVE
        dialBuilder.setPositiveButton(context.getString(R.string.remove)) { _, _ ->
            viewModel.clearFavourites()
            func()
        }


        dialBuilder.setNeutralButton(context.getString(R.string.cancel)) { _, _ ->
        }
        val alertDialog = dialBuilder.create()
        alertDialog.show()
        //alertDialog.setCustomView()
    }


}
