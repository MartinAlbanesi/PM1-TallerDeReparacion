package com.practice.tallerdereparacion.entities

import java.time.LocalDate

data class Repair (val code : Int, val clientCode : Int, val completionDate : LocalDate, val sparePartsUsed : Map<Int,Int>, val hoursWorked : Int){

}