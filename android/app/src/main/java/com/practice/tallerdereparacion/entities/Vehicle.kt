package com.practice.tallerdereparacion.entities

data class Vehicle(
    val numberPlate: String,
    val clientCode: Int,
    val insuranceCap: Double?,
    val insuranceCoverageFrom: Double?
) {

}