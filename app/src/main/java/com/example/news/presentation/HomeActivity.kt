package com.example.news.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.news.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                supportFragmentManager
            )
        view_pager.adapter = sectionsPagerAdapter
        tabLayout.setupWithViewPager(view_pager)
        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_list)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_star_favourite)
    }
}