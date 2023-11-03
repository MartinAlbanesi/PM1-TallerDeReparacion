package com.practice.tallerdereparacion.entities

data class Client (val code : Int, val name : String, val surname : String, var discount : Discount = Discount.NUEVO){


}