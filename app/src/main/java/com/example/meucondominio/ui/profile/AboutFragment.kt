package com.example.meucondominio.ui.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.meucondominio.R
import com.example.meucondominio.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AboutFragment : Fragment(){
    /**
     * Initialize newInstance for passing paameters
     */
  /*  companion object {
        fun newInstance(): AboutFragment {
            var profileFragment = AboutFragment()
            var args = Bundle()
            profileFragment.arguments = args
            return profileFragment
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.about_fragment, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view!!, savedInstanceState)

        var textName = view!!.findViewById(R.id.etName) as EditText
        var textEmail = view!!.findViewById(R.id.etEmail) as EditText
        var textPhone = view!!.findViewById(R.id.etPhone) as EditText
        var textApartment = view!!.findViewById(R.id.etUnity) as EditText

        var confirm = view!!.findViewById<Button>(R.id.btConfirm)
        confirm.setOnClickListener{updateUser()}
    }

    private fun updateUser() {

        var user = Usuario()

         user.name = etName.text.toString()
         user.email = etEmail.text.toString()
         user.phone = etPhone.text.toString()
         user.apartment = etApartment.text.toString()

        FirebaseDatabase.getInstance().getReference("Usuario")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

            .setValue(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                     Toast.makeText(context,
                        "Usuário atualizado com sucesso!",
                        Toast.LENGTH_LONG).show()
                    //val intent = Intent()
                    //intent.putExtra("email", etEmail.text.toString())
                    //intent.putExtra("senha", password.text.toString())
                    //setResult(Activity.RESULT_OK,intent)

                } else {
                    Toast.makeText(context,
                        it.exception?.message,
                        Toast.LENGTH_LONG).show()
                }

            }
    }*/
}