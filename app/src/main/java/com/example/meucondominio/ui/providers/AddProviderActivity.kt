package com.example.meucondominio.ui.providers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.example.meucondominio.R
import com.example.meucondominio.model.Provider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_provider.*

class AddProviderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_provider)

        btConfirmProvider.setOnClickListener{savePrpviderAtRTDB()}
        btCancelAddProvider.setOnClickListener{finish()}
    }

    private fun savePrpviderAtRTDB() {

        var provider = Provider()

        provider.userAddedProvider = FirebaseAuth.getInstance().currentUser!!.email!!.toString()
        provider.providerTitle = etAddProviderTitle.text.toString()
        provider.providerDescription = etAddProviderDesc.text.toString()
        provider.providerPhone = etAddProviderPhone.text.toString()

        FirebaseDatabase.getInstance().getReference("Providers")
            .push()
            .setValue(provider)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this,
                        getString(R.string.AddSuccess),
                        Toast.LENGTH_LONG).show()
                    val intent = Intent()

                    setResult(Activity.RESULT_OK,intent)
                    finish()

                } else {
                    Toast.makeText(this,
                        it.exception?.message,
                        Toast.LENGTH_LONG).show()
                }

            }
    }
}
