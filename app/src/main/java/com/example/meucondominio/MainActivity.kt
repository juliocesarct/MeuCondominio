package com.example.meucondominio

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.FrameLayout
import com.example.meucondominio.ui.home.HomeFragment
import com.example.meucondominio.ui.about.AboutFragment
import com.example.meucondominio.ui.poi.PoiFragment
import com.example.meucondominio.ui.providers.ProvidersFragment
import com.google.firebase.messaging.FirebaseMessaging
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    private var content: FrameLayout? = null

    private val mOnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.navigation_home -> {
                    val fragment = HomeFragment.newInstance()
                    addFragment(fragment)
                    return true
                }

                R.id.navigation_providers -> {
                    val fragment = ProvidersFragment.newInstance()
                    addFragment(fragment)
                    return true
                }

                R.id.navigation_poi -> {
                    val fragment = PoiFragment.newInstance()
                    addFragment(fragment)
                    return true
                }
                R.id.navigation_about -> {
                    val fragment = AboutFragment.newInstance()
                    addFragment(fragment)
                    return true
                }
            }
            return false
        }

    }


    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.content, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)

        content = findViewById(R.id.content) as FrameLayout
        val navigation = findViewById(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


        val fragment = HomeFragment.Companion.newInstance()
        addFragment(fragment)

        FirebaseMessaging.getInstance().isAutoInitEnabled = true
    }

    private fun setUpDagger() {
        AndroidInjection.inject(this)
    }

}