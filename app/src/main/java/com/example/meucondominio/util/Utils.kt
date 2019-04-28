package com.example.meucondominio.util

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.text.TextUtils
import android.widget.Toast
import com.example.meucondominio.MainActivity
import com.example.meucondominio.R
import com.google.firebase.auth.FirebaseAuth

interface Utils {

    fun validFields(emailStr: String , passwordStr: String, context: Context ): Boolean {

        var cancel = false

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr) && !cancel){
            Toast.makeText(context,
                R.string.error_field_required,
                Toast.LENGTH_LONG).show()
            cancel = true
        }
        if (!isEmailValid(emailStr) && !cancel) {
            Toast.makeText(context,
                R.string.error_invalid_email,
                Toast.LENGTH_LONG).show()
            cancel = true
        }

        if (TextUtils.isEmpty(passwordStr) && !cancel) {
            Toast.makeText(context,
                R.string.error_field_required,
                Toast.LENGTH_LONG).show()
            cancel = true
        }

        if (!isPasswordValid(passwordStr) && !cancel) {
            Toast.makeText(context,
                R.string.error_invalid_password,
                Toast.LENGTH_LONG).show()
            cancel = true
        }

        return !cancel
    }


    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    fun startLogin(email: String, password: String, context: Context, mAuth: FirebaseAuth) {
        // Show a progress spinner, and kick off a background task to
        // perform the user login attempt.
        var resultado: Boolean = false

        if (validFields(email, password, context)){
            //attempt authentication against a network service.
            try {
                mAuth.signInWithEmailAndPassword(
                    email,
                    password
                ).addOnCompleteListener{
                    resultado = it.isSuccessful
                    if(!resultado){
                        Toast.makeText(context,
                            it.exception?.message,
                            Toast.LENGTH_LONG).show()
                    }else{
                        goToMain(context)
                    }
                }

            } catch (e: InterruptedException) {
                print(e.stackTrace.toString())
            }
        }
    }

    fun goToMain(context: Context){
        val telaSeguinte  = Intent(context, MainActivity::class.java)

        startActivity(context,telaSeguinte,null)
    }

}