package com.example.meucondominio.ui.home

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.meucondominio.model.News
import kotlinx.android.synthetic.main.home_row.view.*

class NewsViewHolder(private val newsView: View,private val listener: (News) -> Unit):RecyclerView.ViewHolder(newsView) {

    fun bind(news: News,
             listener: (News) -> Unit) = with(itemView) {
        newsView.tvUser?.text = news.user
        newsView.tvTitleNews?.text = news.title
        newsView.tvDescription?.text = news.description
        setOnClickListener{listener(news)}
    }

}