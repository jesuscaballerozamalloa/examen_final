package com.example.practica02_ruizcoloma

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.practica02_ruizcoloma.Login.LoginActivity
import com.example.practica02_ruizcoloma.databinding.ActivityOpcionesLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Opciones_Login : AppCompatActivity() {

    private lateinit var binding: ActivityOpcionesLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpcionesLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()


        binding.IngresarEmail.setOnClickListener{
            startActivity(Intent(this@Opciones_Login, LoginActivity::class.java))
        }

        binding.IngresarGoogle.setOnClickListener {
            Toast.makeText(
                this,"Esta opción no esta disponible por el momento." +
                        "Por favor, ingrese con Email",
                Toast.LENGTH_SHORT).show()
        }

    }

    private fun comprobarSesion() {
        if(firebaseAuth.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }
    }


}