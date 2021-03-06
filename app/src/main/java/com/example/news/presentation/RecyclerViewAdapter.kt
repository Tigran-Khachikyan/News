package com.example.news.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.domain.models.ModelApi
import com.example.news.domain.models.ModelDb
import com.example.news.domain.use_cases.OnAdapterFeaturesChangeListener

class RecyclerViewAdapter(
    var articles: List<ModelDb>?,
    private val onFavoriteChangeListener: OnAdapterFeaturesChangeListener?
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ArticleHolder>() {

    inner class ArticleHolder(
        itemView: View,
        onFavoriteChangeListener: OnAdapterFeaturesChangeListener?
    ) : RecyclerView.ViewHolder(itemView) {

        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val star: ImageView = itemView.findViewById(R.id.iv_star)

        init {
            star.setOnClickListener {
                val article = articles?.get(adapterPosition)
                article?.let {
                    it.isFavourite = !it.isFavourite
                    if (it.isFavourite) onFavoriteChangeListener?.addIntoFavourites(it)
                    else onFavoriteChangeListener?.removeFromFavourites(it.id)
                }
            }
            itemView.setOnClickListener {
                val article = articles?.get(adapterPosition)
                article?.let {
                    onFavoriteChangeListener?.openWebFragment(it.id)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.holder_article, parent, false)
        return ArticleHolder(view, onFavoriteChangeListener)
    }

    override fun getItemCount(): Int = articles?.size ?: 0

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        val currentUser = articles?.get(position)
        currentUser?.let {
            holder.tvCategory.text = currentUser.sectionName
            holder.tvTitle.text = currentUser.webTitle
            holder.star.setFavourite(currentUser.isFavourite)
        }
    }

    private fun ImageView.setFavourite(selected: Boolean) {
        setImageResource(if (selected) R.drawable.ic_star_favourite else R.drawable.ic_star)
    }

}