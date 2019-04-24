package com.example.meucondominio.ui.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meucondominio.R.*

import android.widget.EditText
import com.example.meucondominio.R

class HomeFragment : Fragment() {
    /**
     * Initialize newInstance for passing paameters
     */
    companion object {
        fun newInstance(): HomeFragment {
            var homeFragment = HomeFragment()
            var args = Bundle()
            homeFragment.arguments = args
            return homeFragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.home_fragment, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view!!, savedInstanceState)

        var editTextHome = view!!.findViewById(R.id.editTextHome) as EditText
    }
}