package com.example.meucondominio.ui.poi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meucondominio.R
import kotlinx.android.synthetic.main.poi_fragment.*

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

        btHospital.setOnClickListener{
            val query = "Hospital"
            val latitudeLongitude = "-23.5565804,-46.662113"
            val geo = "geo:$latitudeLongitude?q=$query"
            showInMap(geo)
        }

        btRestaurant.setOnClickListener {
            val query = "Restaurante"
            val latitudeLongitude = "-23.5565804,-46.662113"
            val geo = "geo:$latitudeLongitude?q=$query"
            showInMap(geo)
        }

        btSchool.setOnClickListener {
            val query = "Escola"
            val latitudeLongitude = "-23.5565804,-46.662113"
            val geo = "geo:$latitudeLongitude?q=$query"
            showInMap(geo)
        }

        btGym.setOnClickListener{
            val query = "Academia"
            val latitudeLongitude = "-23.5565804,-46.662113"
            val geo = "geo:$latitudeLongitude?q=$query"
            showInMap(geo)
        }
    }

    fun showInMap(geo:String){
        val geoURI = Uri.parse(geo)
        val intent = Intent(Intent.ACTION_VIEW,geoURI)
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }

}
