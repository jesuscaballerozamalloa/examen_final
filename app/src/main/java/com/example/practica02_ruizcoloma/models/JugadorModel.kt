package com.example.practica02_ruizcoloma.models

import java.io.Serializable

data class JugadorModel(
    val id: String,
    val dorsal: String,
    val nombreJugador:String,
    val pais:String,
    val posicion:String,
    val urlImagen:String,

    ) : Serializable