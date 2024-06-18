package com.example.practica02_ruizcoloma.Fragmentos

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.practica02_ruizcoloma.databinding.FragmentInicioBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.io.ByteArrayOutputStream


class FragmentInicio : Fragment() {
    private lateinit var binding: FragmentInicioBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Subiendo datos...")
        progressDialog.setCancelable(false)

        cargarPaises()

        binding.btnGrabarJugador.setOnClickListener {
            guardarJugador()
        }
    }

    private fun cargarPaises() {
        firestore.collection("Paises")
            .get()
            .addOnSuccessListener { result ->
                val paises = result.map { it.getString("nombre_pais") ?: "" }
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, paises)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.SPNPais.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Error al cargar los países", Toast.LENGTH_SHORT).show()
            }
    }

    private fun guardarJugador() {
        val pais = binding.SPNPais.selectedItem.toString()
        val nombreJugador = binding.ETNombreJugador.text.toString()
        val posicion = binding.ETPosicion.text.toString()
        val dorsal = binding.ETDorsal.text.toString()
        val urlImagen = binding.ETUrlImagen.text.toString()

        if (nombreJugador.isEmpty() || posicion.isEmpty() || dorsal.isEmpty() || urlImagen.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        progressDialog.show()

        // Descargar la imagen de la URL y subirla a Firebase Storage
        Picasso.get().load(urlImagen).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                bitmap?.let {
                    val baos = ByteArrayOutputStream()
                    it.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val data = baos.toByteArray()

                    val storageRef = storage.reference.child("images/${System.currentTimeMillis()}.jpg")
                    val uploadTask = storageRef.putBytes(data)
                    uploadTask.addOnSuccessListener { taskSnapshot ->
                        storageRef.downloadUrl.addOnSuccessListener { uri ->
                            val jugador = hashMapOf(
                                "pais" to pais,
                                "nombreJugador" to nombreJugador,
                                "posicion" to posicion,
                                "dorsal" to dorsal,
                                "urlImagen" to uri.toString()
                            )

                            firestore.collection("jugadores")
                                .add(jugador)
                                .addOnSuccessListener {
                                    Toast.makeText(requireContext(),
                                        "Jugador guardado con éxito",
                                        Toast.LENGTH_SHORT).show()
                                    limpiarCampos()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(requireContext(),
                                        "Error al guardar el jugador",
                                        Toast.LENGTH_SHORT).show()
                                }
                                .addOnCompleteListener{
                                    progressDialog.dismiss()
                                }
                        }
                    }.addOnFailureListener {
                        Toast.makeText(requireContext(),
                            "Error al subir la imagen",
                            Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                    }
                }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Toast.makeText(requireContext(),
                    "Error al descargar la imagen", Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

            }
        })
    }

    private fun limpiarCampos() {
        binding.ETNombreJugador.text.clear()
        binding.ETPosicion.text.clear()
        binding.ETDorsal.text.clear()
        binding.ETUrlImagen.text.clear()
    }
}