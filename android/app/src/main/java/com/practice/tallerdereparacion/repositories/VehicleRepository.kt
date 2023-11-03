package com.practice.tallerdereparacion.repositories

import com.practice.tallerdereparacion.entities.*

object VehicleRepository {

    private var vehicles : MutableList<Vehicle> = mutableListOf()
    private var abstractVehicles : MutableList<AbstractVehicle> = mutableListOf()
    private var vehiclesWithoutInsurance : MutableList<VehicleWithoutInsurance> = mutableListOf()
    private var vehiclesWithInsurance : MutableList<VehicleWithInsurance> = mutableListOf()
    init {
        // entities.Vehicle(numberPlate, clientCode, insuranceCap, insuranceCoverageFrom)
        vehicles.add(Vehicle("FGH 698", 1, 10000.0, 10000.0))
        vehicles.add(Vehicle("EGN 234", 2, 5000.0, 400.0))
        vehicles.add(Vehicle("QAW 531", 3, 30000.0, 4000.0))
        vehicles.add(Vehicle("OIH 874", 4, null, null))
    }

    fun get(numberPlate: String) : Vehicle? {
        for (vehicle in vehicles){
            if(vehicle.numberPlate == numberPlate)
                return vehicle
        }
        return  null
    }

    fun get(clientCode: Int) : AbstractVehicle? {
        for(vehicle in abstractVehicles){
            if(vehicle.clientCode == clientCode){
                return vehicle
            }
        }
        return null
    }

    fun get() : MutableList<Vehicle> {
        return vehicles
    }

    private fun combinarListas(lista1: MutableList<VehicleWithoutInsurance>, lista2: MutableList<VehicleWithInsurance>): MutableList<AbstractVehicle> {
        val listaCombinada = mutableListOf<AbstractVehicle>()
        listaCombinada.addAll(lista1)
        listaCombinada.addAll(lista2)
        return listaCombinada
    }

    fun verificarVehiculosConOSinSeguro() {
        for(vehicle in vehicles){
            if(vehicle.insuranceCap == null && vehicle.insuranceCoverageFrom == null) {
                val vehicleWithoutInsurance = VehicleWithoutInsurance(
                    vehicle.numberPlate,
                    vehicle.clientCode,
                    vehicle.insuranceCap,
                    vehicle.insuranceCoverageFrom
                )
                vehiclesWithoutInsurance.add(vehicleWithoutInsurance)
            }
            else{
                val vehicleWithInsurance = VehicleWithInsurance(
                    vehicle.numberPlate,
                    vehicle.clientCode,
                    vehicle.insuranceCap,
                    vehicle.insuranceCoverageFrom
                )
                vehiclesWithInsurance.add(vehicleWithInsurance).toString()
            }
        }
        val list = combinarListas(vehiclesWithoutInsurance,vehiclesWithInsurance)
        abstractVehicles.addAll(list)
    }
}