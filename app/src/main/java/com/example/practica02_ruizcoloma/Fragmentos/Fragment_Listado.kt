package com.example.practica02_ruizcoloma.Fragmentos

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.entregapp.adapters.JugadorAdapter
import com.example.practica02_ruizcoloma.R
import com.example.practica02_ruizcoloma.models.JugadorModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_Listado.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_Listado : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Fragment_Listado", "en onCreateView")
        val view: View =  inflater.inflate(R.layout.fragment__listado, container, false)
        val rvProyectos: RecyclerView = view.findViewById(R.id.rvJugadores)
        val db = FirebaseFirestore.getInstance()
        var lstProyectos: List<JugadorModel>

        db.collection("jugadores")
            .addSnapshotListener{snap,e->
                if(e!=null){
                    Snackbar
                        .make(view
                            ,"Error al obtener la colección"
                            , Snackbar.LENGTH_LONG).show()
                    return@addSnapshotListener
                }
                lstProyectos = snap!!.documents.map { documentSnapshot ->
                    JugadorModel(
                        documentSnapshot.id,
                        documentSnapshot["dorsal"].toString(),
                        documentSnapshot["nombreJugador"].toString(),
                        documentSnapshot["pais"].toString(),
                        documentSnapshot["posicion"].toString(),
                        documentSnapshot["urlImagen"].toString(),
                    )
                }
                val adapter = JugadorAdapter(lstProyectos)
                rvProyectos.adapter = adapter
                rvProyectos.layoutManager = LinearLayoutManager(requireContext())
                adapter.setOnItemClickListener(object : JugadorAdapter.OnItemClickListener {
                    override fun onItemClick(jugador: JugadorModel) {
                        //
                    }
                })
            }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment_Listado.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment_Listado().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}