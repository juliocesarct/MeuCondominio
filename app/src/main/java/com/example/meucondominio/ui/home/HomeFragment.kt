package com.example.meucondominio.ui.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meucondominio.R.layout.home_fragment
import com.example.meucondominio.model.News
import com.firebase.ui.database.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.home_row.view.*


class HomeFragment : Fragment() {

    lateinit var mRecyclerView : RecyclerView
    lateinit var mDatabase : DatabaseReference

    companion object {
        fun newInstance(): HomeFragment {
            var homeFragment = HomeFragment()
            var args = Bundle()
            homeFragment.arguments = args
            return homeFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(home_fragment, container, false)
        val linearLayoutManager = LinearLayoutManager(context)

        mDatabase = FirebaseDatabase.getInstance().getReference().child("News")
        mRecyclerView = rootView.findViewById(com.example.meucondominio.R.id.listNews)

        linearLayoutManager.orientation = VERTICAL
        mRecyclerView.setLayoutManager(linearLayoutManager)

        logRecyclerView()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view!!, savedInstanceState)

    }

    private fun logRecyclerView(){
        var options = FirebaseRecyclerOptions.Builder<News>().setQuery(mDatabase,News::class.java).build()
        var fbRecyclerAdapter = object : FirebaseRecyclerAdapter<News,NewsViewHolder>(options){


            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NewsViewHolder {
                val v = LayoutInflater.from(p0?.context).inflate(home_fragment, p0, false)
                return NewsViewHolder(v)
            }

            override fun onBindViewHolder(holder: NewsViewHolder, position: Int, model: News) {
                holder.tvUser.setText(model.user)
                holder.tvTitle.setText(model.title)
                holder.tvDescription.setText(model.description)
            }

        }

        mRecyclerView.adapter = fbRecyclerAdapter

    }
}