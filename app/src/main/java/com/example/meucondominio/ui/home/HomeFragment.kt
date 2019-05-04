package com.example.meucondominio.ui.home

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meucondominio.R.layout.home_fragment
import com.example.meucondominio.R.layout.home_row
import com.example.meucondominio.model.News
import com.firebase.ui.database.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.home_fragment.*

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

        mRecyclerView = rootView.findViewById(com.example.meucondominio.R.id.listNews)
        mDatabase = FirebaseDatabase.getInstance().reference.child("News")
        mRecyclerView.layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?

        logRecyclerView()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view!!, savedInstanceState)

        btAddNew.setOnClickListener{telaAddNews()}

    }

    fun telaAddNews(){

        val addNewsAct  = Intent(context, AddNewsActivity::class.java)

        startActivity(addNewsAct)

    }

    private fun logRecyclerView(){

        var options = FirebaseRecyclerOptions.Builder<News>().setQuery(mDatabase,News::class.java).setLifecycleOwner(this).build()
        var fbRecyclerAdapter = object :FirebaseRecyclerAdapter<News,NewsViewHolder>(options){
            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NewsViewHolder {
                val v = LayoutInflater.from(p0?.context).inflate(home_row, p0, false)
                return NewsViewHolder(v)
            }

            override fun onBindViewHolder(holder: NewsViewHolder, position: Int, model: News) {
                holder.bind(model)
            }

        }

        mRecyclerView.adapter = fbRecyclerAdapter
        mRecyclerView.adapter?.notifyDataSetChanged()

    }
}