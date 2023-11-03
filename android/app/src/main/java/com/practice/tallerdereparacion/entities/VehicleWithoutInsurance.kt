package com.practice.tallerdereparacion.entities

class VehicleWithoutInsurance (numberPlate : String,clientCode : Int,insuranceCap : Double?,insuranceCoverageFrom : Double?) : AbstractVehicle(numberPlate,clientCode,insuranceCap,insuranceCoverageFrom) {

    override fun aplicarSeguro(va: Double): Double {
        return 0.0
    }

}