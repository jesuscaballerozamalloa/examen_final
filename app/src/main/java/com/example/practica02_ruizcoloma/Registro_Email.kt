package com.example.practica02_ruizcoloma

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.practica02_ruizcoloma.databinding.ActivityRegistroEmailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Registro_Email : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroEmailBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegistroEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Crear Instancia de Firebase autentication
        firebaseAuth= FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false) // Cuando cargue el registro aparezca el progress dialogo y no se oculte cuando el usuario presiona fuera de èl

        binding.BtnRegistrar.setOnClickListener {
            validarInfo()
        }

    }
    //String para almacenar informaciòn ingresada por el usuario
    private var email = ""
    private var password = ""
    private var repeat_password = ""

    private fun validarInfo() {
        email = binding.EtEmail.text.toString().trim() // se utiliza el trim para que no ocupe los espacios en blanco que deja el usuario
        password = binding.EtPassword.text.toString().trim()
        repeat_password= binding.EtRepeatPassword.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.EtEmail.error = "Email invalido"
            binding.EtEmail.requestFocus()
        }
        else if(email.isEmpty()){
            binding.EtEmail.error="Ingrese email"
            binding.EtEmail.requestFocus()
        }
        else if (password.isEmpty()){
            binding.EtPassword.error= "Ingrese Contraseña"
            binding.EtPassword.requestFocus()
        }
        else if (repeat_password.isEmpty()){
            binding.EtPassword.error="Repita su contraseña"
            binding.EtRepeatPassword.requestFocus()
        }

        else if (password != repeat_password) {
            binding.EtRepeatPassword.error = "Las contraseñas ingresadas no son iguales"
            binding.EtRepeatPassword.requestFocus()
        }

        else{
            registrarusuario()
        }

    }

    private fun registrarusuario() {
        progressDialog.setMessage("Creando su Cuenta")
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                llenarInfoBD()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(this,
                    "No se realizó el registro debido a ${e.message}",
                Toast.LENGTH_SHORT).show()

            }

    }

    private fun llenarInfoBD() {
        progressDialog.setMessage("Guardando Informacion")

        val tiempo = Constantes.obtenertiempodivice()
        val emailUsuario = firebaseAuth.currentUser!!.email
        val uidUsuario = firebaseAuth.uid

        val hashMap = HashMap <String, Any>()
        hashMap ["proveedor"] = "Email"
        hashMap ["tiempo"]= tiempo
        hashMap["email"]= "${emailUsuario}"
        hashMap ["uid"]= "${uidUsuario}"

        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(uidUsuario!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se registró al usuario debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }


}