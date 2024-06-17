package com.example.practica02_ruizcoloma.Login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.practica02_ruizcoloma.Registro_Email
import com.example.practica02_ruizcoloma.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.TxtRegistrarme.setOnClickListener {
            startActivity(Intent(this@LoginActivity,Registro_Email::class.java))
        }

    }
}