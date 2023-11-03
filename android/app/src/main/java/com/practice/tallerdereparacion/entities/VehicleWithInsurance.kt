package com.practice.tallerdereparacion.entities

class VehicleWithInsurance (numberPlate : String,clientCode : Int,insuranceCap : Double?,insuranceCoverageFrom : Double?) : AbstractVehicle(numberPlate,clientCode,insuranceCap,insuranceCoverageFrom) {

    override fun aplicarSeguro(valor: Double): Double {
        val covertura = { valorActual: Double -> valorActual - insuranceCap!! }
        var total = 0.0
        if(valor > insuranceCoverageFrom!!){
            total = covertura(valor)

            if(total < 0.0){
                total = 0.0
            }
            if(total > insuranceCap!!){
                return insuranceCap
            }
        }
        return total
    }
}