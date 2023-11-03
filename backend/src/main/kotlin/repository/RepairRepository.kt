package repository

import entities.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.util.*
import kotlin.math.abs

object RepairRepository {

    private var repairs: MutableList<Repair> = mutableListOf()

    init {
        // mutableMapOf(sparePartCode to unitsUsed, ...)
        val sparePartsUsedIn1 = mutableMapOf(1 to 1, 2 to 2)
        val sparePartsUsedIn2 = mutableMapOf(2 to 2)
        val sparePartsUsedIn3 = mutableMapOf(2 to 1, 3 to 1)
        val sparePartsUsedIn4 = mutableMapOf(3 to 1)
        val sparePartsUsedIn5 = mutableMapOf(2 to 1)
        val sparePartsUsedIn6 = mutableMapOf(1 to 1, 2 to 2, 4 to 1)
        val sparePartsUsedIn7 = mutableMapOf(1 to 1, 2 to 4)

        // entities.Repair(code, clientCode, completionDate, sparePartsUsed, hoursWorked)
        repairs.add(Repair(1, 1, LocalDate.of(2021, Month.APRIL, 15), sparePartsUsedIn1, 10))
        repairs.add(Repair(2, 3, LocalDate.of(2021, Month.APRIL, 20), sparePartsUsedIn2, 8))
        repairs.add(Repair(3, 1, LocalDate.of(2021, Month.APRIL, 21), sparePartsUsedIn3, 5))
        repairs.add(Repair(4, 2, LocalDate.of(2021, Month.MAY, 5), sparePartsUsedIn4, 1))
        repairs.add(Repair(5, 3, LocalDate.of(2021, Month.MAY, 10), sparePartsUsedIn5, 3))
        repairs.add(Repair(6, 4, LocalDate.of(2021, Month.MAY, 12), sparePartsUsedIn6, 3))
        repairs.add(Repair(7, 3, LocalDate.of(2021, Month.MAY, 16), sparePartsUsedIn7, 5))
    }

    //  METODOS:

    //  GETTERS

    //Devuelve una reparacion según el código de reparacion
    fun get(code: Int): Repair? {
        for (repair in repairs) {
            if (repair.code == code)
                return repair
        }
        return null
    }

    //Devuelve la lista de reparaciones
    fun get(): MutableList<Repair> {
        return repairs
    }

    //Devuelve la cantidad de reparaciones que realizo un cliente específico
    private fun getReparacionesPorCliente(clientCode: Int): Int {
        var cant = 0
        for (repair in repairs) {
            if (repair.clientCode == clientCode) {
                cant++
            }
        }
        return cant
    }

    //  OPERACIONES

    private fun calcularTotalCostosSegunCodigoDeReparacion(code: Int): Double {
        var total = 0.0
        for (repair in repairs) {
            if (repair.code == code) {
                for (sparePartsUsed in repair.sparePartsUsed) {
                    val sparePart = SparePartRepository.get(sparePartsUsed.key)
                    for (i in 1..sparePartsUsed.value) {
                        total += sparePart?.price!!
                    }
                }
            }
        }
        return total
    }


    //Calcula el costo de la mano de obra por hora, donde 1hr son $500
    private fun calcularManoDeObra(code: Int): Double {
        var costoManoDeObra = 0.0
        for (repair in repairs) {
            if (repair.code == code) {
                costoManoDeObra = repair.hoursWorked * 500.0
            }
        }
        return costoManoDeObra
    }

    //Verifica si el cliente realizo al menos una reparacion en el taller. Si es así, se convierte en un cliente regular
    private fun verificarSiEsClienteRegular(codCliente: Int) {
        val cantidad = getReparacionesPorCliente(codCliente)
        val client = ClientRepository.get(codCliente)
        if (cantidad > 1)
            if (client != null) {
                client.discount = Discount.REGULAR
            }
    }

    private fun calcularDescuentoSegunTipoDeCliente(codCliente: Int, codReparacion: Int): MutableMap<Double, Double> {
        val client = ClientRepository.get(codCliente)
        var total = 0.0
        var totalDiscount = 0.0
        if (client != null) {
            verificarSiEsClienteRegular(client.code)
        }
        for (repair in repairs) {
            if (repair.clientCode == codCliente) {
                total = calcularTotalCostosSegunCodigoDeReparacion(codReparacion) + calcularManoDeObra(codReparacion)
                if (total > 15000.0) {
                    if (client != null) {
                        totalDiscount = client.discount.apply(total) - total
                        total = client.discount.apply(total)
                    }
                }
            }
        }
        return mutableMapOf(totalDiscount to total)
    }

    //Ordena los datos y muestra la factura según el código de reparación
    fun mostrarFactura(repairCode: Int, clientCode: Int?) {
        var client: Client? = null
        val repair: Repair? = get(repairCode)
        val vehicle = repair?.let { VehicleRepository.get(it.clientCode) }
        val sdf = SimpleDateFormat("dd-M-yyyy")
        val currentDate = sdf.format(Date())
        val descuentoTotalSobrePrecioFinal: MutableMap<Double, Double>?
        var descuentoFinalPorCliente = 0.0
        var precioTotal = 0.0
        var coverturaPorSeguro: Double

        //Si el codigo de cliente ingresado por parámetro es null, se le asigna el código de cliente perteneciente a la reparación
        if (clientCode == null) {
            if (repair != null) {
                client = ClientRepository.get(repair.clientCode)
            }
        } else {
            client = ClientRepository.get(clientCode)
        }

        if (repair != null && client!=null && vehicle!=null) {
                descuentoTotalSobrePrecioFinal = calcularDescuentoSegunTipoDeCliente(client.code, repair.code)
                if ((repair.clientCode == clientCode) || (repair.clientCode == client.code)) {
                    for (repair in repairs) {
                        if (repair.code == repairCode) {
                                verificarSiEsClienteRegular(repair.clientCode)
                                for (descuentos in descuentoTotalSobrePrecioFinal) {
                                    descuentoFinalPorCliente = descuentos.key
                                    precioTotal = descuentos.value
                                }

                                coverturaPorSeguro = vehicle.aplicarSeguro(precioTotal)

                                //Inicio de factura
                                println("Fecha de emisión: $currentDate, fecha de reparacion: ${repair.completionDate}")
                                println("Reparacion #${repair.code}")
                                println("")
                                println("La siguiente factura corresponde al cliente #${repair.code}: ${client.name} ${client.surname}")
                                println("El vehiculo ingresado tiene la patente ${vehicle.numberPlate}")
                                if (vehicle is VehicleWithInsurance) {
                                    println("¿Tiene seguro? SI")
                                } else if (vehicle is VehicleWithoutInsurance) {
                                    println("¿Tiene seguro? NO")
                                }
                                println("")
                                println("Los repuestos utilizados en la reparacion fueron:")

                                //Repuestos
                                for (sparePartsUsed in repair.sparePartsUsed) {
                                    val sparePart = SparePartRepository.get(sparePartsUsed.key)
                                    println("#${sparePart!!.code}: ${sparePart.name}")
                                    println("Precio por unidad: ${sparePart.price}")
                                    println("Unidades utilizadas: ${sparePartsUsed.value}")
                                    println("Subtotal: ${sparePart.price * sparePartsUsed.value}")
                                    println("")
                                }
                                println("Subtotal de repuestos: ${calcularTotalCostosSegunCodigoDeReparacion(repairCode)}")
                                println("")

                                //Mano de obra
                                println("La mano de obra:")
                                println("Horas trabajadas: ${repair.hoursWorked}")
                                println("Monto por hora: 500")
                                println("Subtotal: ${calcularManoDeObra(repairCode)}")
                                println("")
                                println("Los descuentos aplicados en la reparacion fueron:")
                                println("Por cliente: ${abs(descuentoFinalPorCliente)}")
                                println("Por seguro: $coverturaPorSeguro")
                                println("")
                                println("Monto total: ${precioTotal - coverturaPorSeguro}")

                        }
                    }
                } else {
                    println("")
                    println("No se encontró la reparación especificada")
                }
        }else{
            println("")
            println("No se encontró la reparación especificada")
        }
    }

}

    