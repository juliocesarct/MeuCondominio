package com.example.meucondominio

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.app.LoaderManager.LoaderCallbacks
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView

import java.util.ArrayList
import android.Manifest.permission.READ_CONTACTS
import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.example.meucondominio.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity(), LoaderCallbacks<Cursor> {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private var mAuthTask: UserLoginTask? = null
    private lateinit var mAuth: FirebaseAuth
    private val CADASTRO_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Set up the login form.

        mAuth = FirebaseAuth.getInstance()

        if(mAuth.currentUser != null){
            goToMain()
        }

        populateAutoComplete()
        /*password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                startLogin()
                return@OnEditorActionListener true
            }
            false
        })*/

        email_sign_in_button.setOnClickListener { startLogin()}
        email_sign_up_button.setOnClickListener { attemptSignUp() }
    }

    private fun populateAutoComplete() {
        if (!mayRequestContacts()) {
            return
        }

        loaderManager.initLoader(0, null, this)
    }

    private fun mayRequestContacts(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(email, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.ok,
                    { requestPermissions(arrayOf(READ_CONTACTS), REQUEST_READ_CONTACTS) })
        } else {
            requestPermissions(arrayOf(READ_CONTACTS), REQUEST_READ_CONTACTS)
        }
        return false
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CADASTRO_REQUEST_CODE ->{
                when(resultCode){
                    Activity.RESULT_OK ->{
                        //vaiParaTelaMenu()
                        email.setText(data?.getStringExtra("email"))
                        password.setText(data?.getStringExtra("senha"))
                    }
                }
            }
        }
    }


    private fun startLogin() {
        // Show a progress spinner, and kick off a background task to
        // perform the user login attempt.
        if (validFields()){
            showProgress(true)
            mAuthTask = UserLoginTask(email.text.toString(), password.text.toString())
            mAuthTask!!.execute(null as Void?)
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */

    private fun validFields(): Boolean {
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false

        // Reset errors.
        email.error = null
        password.error = null

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr) && !cancel){
            email.error = getString(R.string.error_field_required)
            email.requestFocus()
            cancel = true
        }
        if (!isEmailValid(emailStr) && !cancel) {
            email.error = getString(R.string.error_invalid_email)
            email.requestFocus()
            cancel = true
        }

        if (TextUtils.isEmpty(passwordStr) && !cancel) {
            password.error = getString(R.string.error_field_required)
            password.requestFocus()
            cancel = true
        }

        if (!isPasswordValid(passwordStr) && !cancel) {
            password.error = getString(R.string.error_invalid_password)
            password.requestFocus()
            cancel = true
        }

        return !cancel
    }

    private fun attemptSignUp() {
        if (mAuthTask != null) {
            return
        }

        // Store values at the time of the login attempt.
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()
        var success = false

        success = validFields()

        if (success) {
            mAuth.createUserWithEmailAndPassword(
            email.text.toString(),
            password.text.toString())
                .addOnCompleteListener {
                if (it.isSuccessful) {
                    salvaNoRealTimeDatabase()
                    startLogin()
                } else {
                    Toast.makeText(this,
                        it.exception?.message,
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun salvaNoRealTimeDatabase() {

        var user = Usuario()
        user.email = email.text.toString()

        FirebaseDatabase.getInstance().getReference("Usuario")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .setValue(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this,
                        "Usuário cadastrado com sucesso!",
                        Toast.LENGTH_LONG).show()
                    val intent = Intent()
                    intent.putExtra("email", email.text.toString())
                    intent.putExtra("senha", password.text.toString())
                    setResult(Activity.RESULT_OK,intent)

                } else {
                    Toast.makeText(this,
                        it.exception?.message,
                        Toast.LENGTH_LONG).show()
                }

            }
    }

    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

            login_form.visibility = if (show) View.GONE else View.VISIBLE
            login_form.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 0 else 1).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_form.visibility = if (show) View.GONE else View.VISIBLE
                    }
                })

            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_progress.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_progress.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_form.visibility = if (show) View.GONE else View.VISIBLE
        }
    }

    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<Cursor> {
        return CursorLoader(
            this,
            // Retrieve data rows for the device user's 'profile' contact.
            Uri.withAppendedPath(
                ContactsContract.Profile.CONTENT_URI,
                ContactsContract.Contacts.Data.CONTENT_DIRECTORY
            ), ProfileQuery.PROJECTION,

            // Select only email addresses.
            ContactsContract.Contacts.Data.MIMETYPE + " = ?", arrayOf(
                ContactsContract.CommonDataKinds.Email
                    .CONTENT_ITEM_TYPE
            ),

            // Show primary email addresses first. Note that there won't be
            // a primary email address if the user hasn't specified one.
            ContactsContract.Contacts.Data.IS_PRIMARY + " DESC"
        )
    }

    override fun onLoadFinished(cursorLoader: Loader<Cursor>, cursor: Cursor) {
        val emails = ArrayList<String>()
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS))
            cursor.moveToNext()
        }

        addEmailsToAutoComplete(emails)
    }

    override fun onLoaderReset(cursorLoader: Loader<Cursor>) {

    }

    private fun addEmailsToAutoComplete(emailAddressCollection: List<String>) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        val adapter = ArrayAdapter(
            this@LoginActivity,
            android.R.layout.simple_dropdown_item_1line, emailAddressCollection
        )

        email.setAdapter<ArrayAdapter<String>?>(adapter)
    }

    private fun goToMain(){
        val telaSeguinte  = Intent(this,MainActivity::class.java)
        startActivity(telaSeguinte)
        finish()
    }

    object ProfileQuery {
        val PROJECTION = arrayOf(
            ContactsContract.CommonDataKinds.Email.ADDRESS,
            ContactsContract.CommonDataKinds.Email.IS_PRIMARY
        )
        val ADDRESS = 0
        val IS_PRIMARY = 1
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    inner class UserLoginTask internal constructor(private val mEmail: String, private val mPassword: String) :
        AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void): Boolean? {

            var resultado: Boolean = false
            //attempt authentication against a network service.
            try {
                mAuth.signInWithEmailAndPassword(
                    mEmail,
                    mPassword
                ).addOnCompleteListener{
                    resultado = it.isSuccessful
                    if(!resultado){
                        Toast.makeText(this@LoginActivity,
                            it.exception?.message,
                            Toast.LENGTH_LONG).show()
                        email.error = getString(R.string.error_access)
                        email.requestFocus()
                    }else{
                        goToMain()
                        finish()
                    }
                }

            } catch (e: InterruptedException) {
                print(e.stackTrace.toString())
                email.error = getString(R.string.error_access)
                email.requestFocus()
                return false
            }
            return resultado
        }

        override fun onPostExecute(success: Boolean?) {
            mAuthTask = null
            showProgress(false)

            if (success!!) {
                goToMain()
                finish()
            }
        }

        override fun onCancelled() {
            mAuthTask = null
            showProgress(false)
        }
    }

    companion object {

        /**
         * Id to identity READ_CONTACTS permission request.
         */
        private val REQUEST_READ_CONTACTS = 0

    }
}
