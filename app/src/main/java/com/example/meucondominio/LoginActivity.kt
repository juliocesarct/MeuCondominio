package com.example.meucondominio

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.meucondominio.util.Utils
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity(), Utils{
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
    */
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Set up the login form.

        mAuth = FirebaseAuth.getInstance()

        if(mAuth.currentUser != null){
            goToMain(this)
            finish()
        }

        email_sign_in_button.setOnClickListener { startLogin(email.text.toString(),password.text.toString(),this,mAuth)}
        email_sign_up_button.setOnClickListener { register() }
    }

    private fun register(){

        val showSignUp  = Intent(this, RegisterActivity::class.java)

        startActivity(showSignUp)
        finish()
    }

}
