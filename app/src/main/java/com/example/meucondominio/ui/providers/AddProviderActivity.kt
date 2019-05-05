package com.example.meucondominio.ui.providers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.example.meucondominio.R
import com.example.meucondominio.model.Provider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_provider.*
import kotlinx.android.synthetic.main.providers_fragment.*

class AddProviderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_provider)

        val providerId : String? = intent.getStringExtra("providerId")
        val user: String? = intent.getStringExtra("modelUser")
        val title: String? = intent.getStringExtra("modelTitle")
        val desc: String? = intent.getStringExtra("modelDesc")
        val phone: String? = intent.getStringExtra("modelPhone")

        if(title != null){etAddProviderTitle.setText(title)}
        if(desc != null){etAddProviderDesc.setText(desc)}
        if(phone != null){etAddProviderPhone.setText(phone)}

        if( providerId == null ) {
            btConfirmProvider.setOnClickListener {
                if (etAddProviderTitle.text.isNotEmpty() and etAddProviderPhone.text.isNotEmpty()) {
                    saveProviderAtRTDB()
                } else {
                    Toast.makeText(this,getString(R.string.error_field_required),Toast.LENGTH_LONG).show()
                }
            }
            btDeleteProvider.visibility = GONE
        }else{

            ivCallProvider.setOnClickListener{startActivity(Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",phone,null)))}

            if(user == FirebaseAuth.getInstance().currentUser!!.email!!.toString()){
                btDeleteProvider.visibility = VISIBLE
                btDeleteProvider.setOnClickListener{ deleteProviderAtRTDB(providerId)}
                btConfirmProvider.setOnClickListener{ updateProviderAtRTDB(providerId)}
            }else{
                etAddProviderTitle.isEnabled = false
                etAddProviderDesc.isEnabled = false
                etAddProviderPhone.isEnabled = false
                btConfirmProvider.visibility = GONE
                btDeleteProvider.visibility = GONE
            }
        }

        btCancelAddProvider.setOnClickListener{finish()}
    }

    private fun saveProviderAtRTDB() {

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

    private fun deleteProviderAtRTDB(id:String) {

        FirebaseDatabase.getInstance().getReference("Providers")
            .child(id)
            .removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this,
                        getString(R.string.deleteSuccess),
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

    private fun updateProviderAtRTDB(id:String) {

        var provider = Provider()

        provider.userAddedProvider = FirebaseAuth.getInstance().currentUser!!.email!!.toString()
        provider.providerTitle = etAddProviderTitle.text.toString()
        provider.providerDescription = etAddProviderDesc.text.toString()
        provider.providerPhone = etAddProviderPhone.text.toString()

        FirebaseDatabase.getInstance().getReference("Providers")
            .child(id)
            .setValue(provider)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this,
                        getString(R.string.changeSuccess),
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
