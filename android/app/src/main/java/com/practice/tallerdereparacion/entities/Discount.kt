package com.practice.tallerdereparacion.entities

enum class Discount(var apply : (Double) -> Double) {
    REGULAR({valor -> valor * 0.85}),
    NUEVO({valor -> valor * 0.6}),
}