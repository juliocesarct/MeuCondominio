package com.example.meucondominio.ui.poi

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meucondominio.R

class PoiFragment : Fragment() {

    companion object {
        fun newInstance(): PoiFragment {
            var poiFragment = PoiFragment()
            var args = Bundle()
            poiFragment.arguments = args
            return poiFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.poi_fragment, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view!!, savedInstanceState)

    }

}
