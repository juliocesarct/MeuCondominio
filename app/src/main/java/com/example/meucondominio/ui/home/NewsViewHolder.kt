package com.example.meucondominio.ui.home

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.meucondominio.model.News
import kotlinx.android.synthetic.main.home_row.view.*

class NewsViewHolder(private val newsView: View, var news: News? = null):RecyclerView.ViewHolder(newsView) {

    fun bind(news: News) {
        newsView.tvUser?.text = news.user
        newsView.tvTitleNews?.text = news.title
        newsView.tvDescription?.text = news.description
    }
    /*
    var user = newsView.findViewById(R.id.tvUser) as TextView
    var title = newsView.findViewById(R.id.tvTitleNews) as TextView
    var description = newsView.findViewById(R.id.tvDescription) as TextView
     */
}