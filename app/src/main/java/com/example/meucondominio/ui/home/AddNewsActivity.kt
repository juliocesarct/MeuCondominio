package com.example.meucondominio.ui.home

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.meucondominio.R
import com.example.meucondominio.model.News
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_news_add.*

class AddNewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_add)

        btAddNews.setOnClickListener{saveNewsAtRTDB()}
        btCancelAddNew.setOnClickListener{finish()}
    }

    private fun saveNewsAtRTDB() {

        var new = News()

        new.user = FirebaseAuth.getInstance().currentUser!!.email!!.toString()
        new.title = etAddTitle.text.toString()
        new.description = etAddDescription.text.toString()

        FirebaseDatabase.getInstance().getReference("News")
            //.child(FirebaseAuth.getInstance().currentUser!!.uid!!)
            .push()
            .setValue(new)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this,
                        getString(R.string.AddNewsSuccess),
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
