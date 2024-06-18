package com.example.practica02_ruizcoloma.Fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.practica02_ruizcoloma.Opciones_Login
import com.example.practica02_ruizcoloma.databinding.FragmentCuentaBinding
import com.google.firebase.auth.FirebaseAuth

class Fragment_Cuenta : Fragment() {
    private lateinit var binding:FragmentCuentaBinding
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCuentaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.BtnCerrarSesion.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(mContext, Opciones_Login:: class.java))
            activity?.finishAffinity()
        }
    }

}