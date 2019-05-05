package com.example.meucondominio.ui.providers

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.meucondominio.model.Provider
import kotlinx.android.synthetic.main.provider_row.view.*

class ProvidersViewHolder(private val providerView: View,private val dialListener: (Provider) -> Unit):RecyclerView.ViewHolder(providerView) {

    fun bind(provider: Provider,
             dialListener: (Provider) -> Unit) = with(itemView) {
        providerView.tvUserAddedP?.text = provider.userAddedProvider
        providerView.tvTitleProvider?.text = provider.providerTitle
        providerView.tvDescProvider?.text = provider.providerDescription
        providerView.tvProviderPhone?.text = provider.providerPhone
        providerView.ivCall?.setOnClickListener{dialListener(provider)}

    }
}