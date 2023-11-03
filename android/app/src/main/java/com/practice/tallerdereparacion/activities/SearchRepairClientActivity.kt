package com.practice.tallerdereparacion.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practice.tallerdereparacion.R
import com.practice.tallerdereparacion.adapters.AdapterRepairs
import com.practice.tallerdereparacion.entities.Repair
import com.practice.tallerdereparacion.repositories.RepairRepository
import com.google.android.material.snackbar.Snackbar

class SearchRepairClientActivity : AppCompatActivity(){

    private val repairs: MutableList<Repair> = ArrayList()
    private lateinit var recycler : RecyclerView
    private lateinit var adapterRepairs: AdapterRepairs
    private lateinit var repairCodeTextView: TextView
    private lateinit var clientCodeTextView: TextView
    private lateinit var fetchButton: Button
    private lateinit var cancelButton: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_repair_client)

        repairCodeTextView = findViewById(R.id.editTextRepairId)
        clientCodeTextView = findViewById(R.id.editTextClientCode)
        fetchButton = findViewById(R.id.searchButton2)
        cancelButton = findViewById(R.id.cancelFilterButton2)

        cancelButton.setOnClickListener { cancelFilter() }
        fetchButton.setOnClickListener{ filterRepairs() }

        buildRecyclerView()
    }

    //Configuración del recycler y setteo de listeners para cada elemento
    @RequiresApi(Build.VERSION_CODES.O)
    fun buildRecyclerView(){
        val selectRepairClickListener = { repair: Repair ->
            val intent = Intent(this, BillActivity::class.java)
            intent.putExtra("repairId",repair.code)
            intent.putExtra("repairClientCode",repair.clientCode)
            startActivity(intent)
        }

        //Settea las reparaciones a la lista repairs
        for(repair in RepairRepository.get()){
            repairs.add(repair)
        }

        recycler = findViewById(R.id.recyclerRepairs)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        adapterRepairs= AdapterRepairs(repairs,selectRepairClickListener)
        recycler.adapter = adapterRepairs
    }

    //Filtra la lista de reparaciones según el código de reparación
    private fun filterRepairs() {
        val repairId = repairCodeTextView.text.toString()
        val clientCode = clientCodeTextView.text.toString()
        val filteredList = adapterRepairs.listRepairs.filter { s -> s.code.toString() == repairId && s.clientCode.toString() == clientCode } as MutableList<Repair>

        //Verifica que los valores del filtro sean válidos
        if(repairId.isEmpty() && clientCode.isEmpty() || repairId.isEmpty() || clientCode.isEmpty()){
            Snackbar.make(recycler, "Completa los campos", Snackbar.LENGTH_SHORT).show()
        }else if(repairId > repairs.size.toString() || repairId == "0" || clientCode == "0"){
            Snackbar.make(recycler, "No se encontró la reparación", Snackbar.LENGTH_SHORT).show()
        }else{
            adapterRepairs.setFiltered(filteredList)
        }

    }

    //Settea los valores iniciales a la lista del Adapter y la actualiza
    private fun cancelFilter(){
        adapterRepairs.setDefaultData()
    }
}
