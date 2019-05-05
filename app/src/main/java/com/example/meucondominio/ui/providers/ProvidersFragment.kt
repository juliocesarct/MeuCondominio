package com.example.meucondominio.ui.providers

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meucondominio.R
import com.example.meucondominio.model.Provider
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.providers_fragment.*

class ProvidersFragment : Fragment() {

    companion object {
        fun newInstance(): ProvidersFragment {
            var providersFragment = ProvidersFragment()
            var args = Bundle()
            providersFragment.arguments = args
            return providersFragment

        }
    }

    lateinit var mRecyclerView : RecyclerView
    lateinit var mDatabase : DatabaseReference
    private var dialListener: (Provider)->Unit = {
        startActivity(Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",it.providerPhone.toString(),null)))}
    private var listener: (Provider)->Unit = {
        var providerIntent = Intent(context, AddProviderActivity::class.java)
        providerIntent.putExtra("modelUser",it.userAddedProvider)
        providerIntent.putExtra("modelTitle",it.providerTitle)
        providerIntent.putExtra("modelDesc",it.providerDescription)
        providerIntent.putExtra("modelPhone",it.providerPhone)
        providerIntent.putExtra("providerId",it.idProvider)
        startActivity(providerIntent)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var rootView = inflater!!.inflate(R.layout.providers_fragment, container, false)

        mRecyclerView = rootView.findViewById(com.example.meucondominio.R.id.listProviders)
        mDatabase = FirebaseDatabase.getInstance().reference.child("Providers")
        mRecyclerView.layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?

        logRecyclerView()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btAddProvider.setOnClickListener{startActivity(Intent(context, AddProviderActivity::class.java))}

    }

    private fun logRecyclerView(){

        var options = FirebaseRecyclerOptions.Builder<Provider>().setQuery(mDatabase, Provider::class.java).setLifecycleOwner(this).build()
        var fbRecyclerAdapter = object : FirebaseRecyclerAdapter<Provider, ProvidersViewHolder>(options){
            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProvidersViewHolder {
                val v = LayoutInflater.from(p0?.context).inflate(R.layout.provider_row, p0, false)

                return ProvidersViewHolder(v,dialListener,listener)
            }

            override fun onBindViewHolder(holder: ProvidersViewHolder, position: Int, model: Provider) {
               model.idProvider = this.getRef(position).key
               holder.bind(model,dialListener,listener)            }

        }

        mRecyclerView.adapter = fbRecyclerAdapter
        mRecyclerView.adapter?.notifyDataSetChanged()

    }
}
