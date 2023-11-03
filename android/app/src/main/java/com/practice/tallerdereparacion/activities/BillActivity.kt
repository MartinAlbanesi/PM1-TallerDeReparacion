package com.practice.tallerdereparacion.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.practice.tallerdereparacion.R
import com.practice.tallerdereparacion.repositories.RepairRepository

class BillActivity : AppCompatActivity() {

    private var repairId: Int = 0
    private var repairClientCode: Int = 0
    private lateinit var textBill: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)

        textBill = findViewById(R.id.textBill)

        repairId = intent.getIntExtra("repairId",0)
        repairClientCode = intent.getIntExtra("repairClientCode",0)

        showBill()

    }

    //Settea al textView el string que devuelve el método mostrarFactura()
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showBill(){
        textBill.text = RepairRepository.mostrarFactura(repairId,repairClientCode)
    }
}