package com.practice.tallerdereparacion.entities

abstract class AbstractVehicle(
    val numberPlate: String,
    val clientCode: Int,
    val insuranceCap: Double?,
    val insuranceCoverageFrom: Double?
) {

    abstract fun aplicarSeguro(va: Double): Double
}