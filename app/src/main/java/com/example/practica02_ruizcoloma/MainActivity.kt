package com.example.practica02_ruizcoloma

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.practica02_ruizcoloma.Fragmentos.FragmentInicio
import com.example.practica02_ruizcoloma.Fragmentos.Fragment_Cuenta
import com.example.practica02_ruizcoloma.Fragmentos.Fragment_Listado
import com.example.practica02_ruizcoloma.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

/**
 * v2
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()


        verFragmentInicio()
        binding.BottonNV.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.Item_Inicio->{
                    verFragmentInicio()
                    true
                }
                R.id.Item_Listado_Player->{
                    verFragmentListado()
                    true
                }
                R.id.Item_Cuenta->{
                    verFragmentCuenta()
                    true
                }

                else->{
                    false
                }
            }
        }
    }

    private fun comprobarSesion(){
        if (firebaseAuth.currentUser == null){
            startActivity(Intent(this, Opciones_Login::class.java))
            finishAffinity()
        }
    }

    private fun verFragmentInicio(){
        binding.TituloRL.text = "Registro Jugadores"
        val fragment =FragmentInicio()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment1.id, fragment, "FragmentInicio")
        fragmentTransition.commit()

    }
    private fun verFragmentListado(){
        Log.d("verFragmentListado", "en verFragmentListado")
        binding.TituloRL.text = "Listado de Jugadores"
        val fragment =Fragment_Listado()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment1.id, fragment, "FragmentListado")
        fragmentTransition.commit()

    }
    private fun verFragmentCuenta(){
        Log.d("verFragmentListado", "en verFragmentListado")
        binding.TituloRL.text = "Cuenta"
        val fragment =Fragment_Cuenta()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment1.id, fragment, "FragmentCuenta")
        fragmentTransition.commit()

    }
}