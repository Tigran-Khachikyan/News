package com.example.news.presentation.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.news.R
import com.example.news.presentation.KEY_ID
import com.example.news.domain.use_cases.OnHolderClickListener
import com.example.news.presentation.web.WebFragment

abstract class BaseFragment : Fragment(), OnHolderClickListener {

    override fun getArticle(id: String) {

        val webFragment = WebFragment()
        webFragment.arguments = Bundle().apply { putString(KEY_ID, id) }

        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.frame, webFragment).addToBackStack(null).commit()
    }
}