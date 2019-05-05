package com.example.meucondominio.ui.home

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View.*
import android.widget.Toast
import com.example.meucondominio.R
import com.example.meucondominio.model.News
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_news.*

class AddNewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val user: String? = intent.getStringExtra("modelUser")
        val newsId : String? = intent.getStringExtra("newsId")
        val title: String? = intent.getStringExtra("modelTitle")
        val desc: String? = intent.getStringExtra("modelDesc")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_news)

        if(title != null){etAddTitle.setText(title)}
        if(desc != null){etAddDescription.setText(desc)}

        if( newsId == null ){
            btAddNews.setOnClickListener { saveNewsAtRTDB() }
            btDeleteNews.visibility = GONE
        }else{
            if(user == FirebaseAuth.getInstance().currentUser!!.email!!.toString()){
                btDeleteNews.visibility = VISIBLE
                btDeleteNews.setOnClickListener{ deleteNewsAtRTDB(newsId)}
                btAddNews.setOnClickListener{ updateNewsAtRTDB(newsId)}
            }else{
                etAddTitle.isEnabled = false
                etAddDescription.isEnabled = false
                btAddNews.visibility = GONE
                btDeleteNews.visibility = GONE
            }
        }

        btCancelAddNew.setOnClickListener{finish()}

    }

    private fun saveNewsAtRTDB() {

        var new = News()

        new.user = FirebaseAuth.getInstance().currentUser!!.email!!.toString()
        new.title = etAddTitle.text.toString()
        new.description = etAddDescription.text.toString()

        FirebaseDatabase.getInstance().getReference("News")
            .push()
            .setValue(new)
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

    private fun deleteNewsAtRTDB(id:String) {

        FirebaseDatabase.getInstance().getReference("News")
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

    private fun updateNewsAtRTDB(id:String) {

        var new = News()

        new.user = FirebaseAuth.getInstance().currentUser!!.email!!.toString()
        new.title = etAddTitle.text.toString()
        new.description = etAddDescription.text.toString()

        FirebaseDatabase.getInstance().getReference("News")
            .child(id)
            .setValue(new)
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
