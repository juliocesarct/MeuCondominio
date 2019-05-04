package com.example.meucondominio

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.meucondominio.model.Usuario
import com.example.meucondominio.util.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), Utils {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //mAuth = FirebaseAuth.getInstance()

        btConfirm.setOnClickListener { attemptSignUp() }
        btCancel.setOnClickListener{goToLogin()}
    }

    fun goToLogin(){

        val telaLogin  = Intent(this, LoginActivity::class.java)

        startActivity(telaLogin)
        finish()
    }

    private fun attemptSignUp() {

        var success: Boolean =  validFields(etEmail.text.toString(), etPassword.text.toString(),this)

        if (success) {
            mAuth.createUserWithEmailAndPassword(
                etEmail.text.toString(),
                etPassword.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        salvaNoRealTimeDatabase()
                        startLogin(etEmail.text.toString(),etPassword.text.toString(),this,mAuth )
                    } else {
                        Toast.makeText(this,
                            it.exception?.message,
                            Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(this,
                R.string.error_field_required,
                Toast.LENGTH_LONG).show()
            etName.requestFocus()
        }
    }

    private fun salvaNoRealTimeDatabase() {

        var user = Usuario()

        user.name =  etName.text.toString()
        user.email = etEmail.text.toString()
        user.phone = etPhone.text.toString()
        user.unity = etUnity.text.toString()

        FirebaseDatabase.getInstance().getReference("Usuario")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .setValue(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this,
                        getString(R.string.registerSuccess),
                        Toast.LENGTH_LONG).show()
                    val intent = Intent()
                    intent.putExtra("email", etEmail.text.toString())
                    intent.putExtra("senha", etPassword.text.toString())
                    setResult(Activity.RESULT_OK,intent)

                } else {
                    Toast.makeText(this,
                        it.exception?.message,
                        Toast.LENGTH_LONG).show()
                }

            }
    }
}
