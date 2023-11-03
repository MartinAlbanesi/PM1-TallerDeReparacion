package com.practice.tallerdereparacion.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.practice.tallerdereparacion.R
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fetchByRepairButton = findViewById<Button>(R.id.fetchByRepairButton)
        val fetchByClientRepairButton = findViewById<Button>(R.id.fetchByClientRepairButton)
        val exitButton = findViewById<Button>(R.id.exitButton)

        fetchByRepairButton.setOnClickListener { fetchByRepairCode() }
        fetchByClientRepairButton.setOnClickListener{ fetchByRepairCodeClientCode()}
        exitButton.setOnClickListener{ exit() }
    }

    //Inicia la actividad SearchRepairActivity
    private fun fetchByRepairCode() {
        val intent = Intent(this, SearchRepairActivity::class.java)
        startActivity(intent)
    }

    //Inicia la actividad SearchRepairClientActivity
    private fun fetchByRepairCodeClientCode(){
        val intent = Intent(this, SearchRepairClientActivity::class.java)
        startActivity(intent)
    }

    //Sale de la app
    private fun exit(){
        finish();
        exitProcess(0);
    }


}