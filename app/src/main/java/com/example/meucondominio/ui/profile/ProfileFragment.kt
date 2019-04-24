package com.example.meucondominio.ui.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.meucondominio.R

class ProfileFragment : Fragment() {
    /**
     * Initialize newInstance for passing paameters
     */
    companion object {
        fun newInstance(): ProfileFragment {
            var profileFragment = ProfileFragment()
            var args = Bundle()
            profileFragment.arguments = args
            return profileFragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.profile_fragment, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view!!, savedInstanceState)

        var textName= view!!.findViewById(R.id.etName) as EditText
        var textEmail = view!!.findViewById(R.id.etEmail) as EditText
        var textPhone = view!!.findViewById(R.id.etPhone) as EditText
        var textApartment = view!!.findViewById(R.id.etApartment) as EditText
    }
}