package com.example.meucondominio.ui.providers

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meucondominio.R

class ProvidersFragment : Fragment() {

    companion object {
        fun newInstance() = ProvidersFragment()
    }

    private lateinit var viewModel: ProvidersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.providers_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProvidersViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
