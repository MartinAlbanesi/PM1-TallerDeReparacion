package com.practice.tallerdereparacion.entities

import com.practice.tallerdereparacion.repositories.RepairRepository.mostrarFactura
import com.practice.tallerdereparacion.repositories.VehicleRepository
import java.lang.RuntimeException

class Menu {

    fun mostrarMenu() {
        var opcion: Int? = null
        var deseaSalir = false
        var opcionInvalida = false

        VehicleRepository.verificarVehiculosConOSinSeguro()


        do {
            do {
                do {
                    println("1. Ingresar cod de cliente y cod de reparacion")
                    println("2. Ingresar cod de reparacion")
                    println("3. Salir")
                    println("Ingresar opcion: ")

                    try {
                        opcion = readLine()!!.toInt()
                    } catch (e: RuntimeException) {
                        println("")
                        println("ERROR: Ingreso de opcion inesperada. Por favor, ingrese una opción válida")
                        println("")
                        opcionInvalida = true
                    }

                } while (opcionInvalida)

                if (opcion == 3) {
                    deseaSalir = true
                }

            } while (opcion != 1 && opcion != 2 && opcion != 3)

            when (opcion) {
                1 -> ingresarCodClienteYCodReparacion()
                2 -> ingresarCodReparacion()
                3 -> println("Saliendo del programa...")
                else -> println("Opcion incorrecta")
            }

            if (opcion != 3) {
                println("")
                println("-------------------")
                println("Volviendo al inicio")
                println("-------------------")
                println("")
            }
        } while (!deseaSalir)
    }

    private fun ingresarCodClienteYCodReparacion() {
        var codCliente: Int? = null
        var codReparacion: Int? = null
        var opcionInvalida = false
        var fueExitoso = false
        do{
            try{
                println("Ingresar cod de cliente: ")
                codCliente = readLine()!!.toInt()
                println("Ingresar cod de reparacion: ")
                codReparacion = readLine()!!.toInt()
            }catch(e: RuntimeException){
                println("")
                println("ERROR: Ingreso de opcion inesperada. Por favor, ingrese nuevamente un código válido")
                println("")
                opcionInvalida = true
            }finally{
                if (codCliente != null && codReparacion != null) {
                    fueExitoso = buscarPorCodClienteYCodReparacion(codCliente, codReparacion)
                }
            }
        }while(opcionInvalida && !fueExitoso)
    }

    private fun ingresarCodReparacion() {
        var codReparacion: Int? = null
        var opcionInvalida = false
        var fueExitoso = false
        do{
            try{
                println("Ingresar cod de reparacion: ")
                codReparacion = readLine()!!.toInt()
            }catch(e: RuntimeException){
                println("")
                println("ERROR: Ingreso de opcion inesperada. Por favor, ingrese nuevamente un código válido")
                println("")
                opcionInvalida = true
            }finally{
                if (codReparacion != null) {
                    fueExitoso = buscarPorCodReparacion(codReparacion)
                }
            }
        }while(opcionInvalida && !fueExitoso)
    }


    private fun buscarPorCodClienteYCodReparacion(codCliente: Int, codReparacion: Int):Boolean {
        val fueExitoso = true
        mostrarFactura(codReparacion, codCliente)
        return fueExitoso
    }


    private fun buscarPorCodReparacion(codReparacion: Int): Boolean {
        val fueExitoso = true
        mostrarFactura(codReparacion, null)
        return fueExitoso
    }

}
