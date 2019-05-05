package com.example.meucondominio.ui.about

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meucondominio.LoginActivity
import com.example.meucondominio.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.about_fragment.*

class AboutFragment : Fragment(){
    /**
     * Initialize newInstance for passing paameters
     */
    companion object {
        fun newInstance(): AboutFragment {
            var aboutFragment = AboutFragment()
            var args = Bundle()
            aboutFragment.arguments = args
            return aboutFragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.about_fragment, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view!!, savedInstanceState)

        btLogOut.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }

    }

}