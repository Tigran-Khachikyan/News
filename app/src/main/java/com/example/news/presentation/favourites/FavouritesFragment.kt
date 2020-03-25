package com.example.news.presentation.favourites

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.news.R
import com.example.news.presentation.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_list.*

class FavouritesFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavouritesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvWarning.text = requireContext().getString(R.string.noFavouriteFound)
        fab.setOnClickListener { it ->
            showDialog(requireContext()) {
                Snackbar.make(
                        it,
                        requireContext().getString(R.string.successfullyRemoved),
                        Snackbar.LENGTH_LONG
                    )
                    .setAction("Action", null).show()
            }
        }

        (viewModel as FavouritesViewModel).favouriteNews.observe(viewLifecycleOwner, Observer {

            tvWarning.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            fab.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
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

        dialBuilder.setPositiveButton(context.getString(R.string.remove)) { _, _ ->
            (viewModel as FavouritesViewModel).clearFavourites()
            func()
        }

        dialBuilder.setNeutralButton(context.getString(R.string.cancel)) { _, _ ->
        }
        val alertDialog = dialBuilder.create()
        alertDialog.show()
    }


}
