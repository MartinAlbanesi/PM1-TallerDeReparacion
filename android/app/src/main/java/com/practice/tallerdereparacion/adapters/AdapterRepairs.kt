package com.practice.tallerdereparacion.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practice.tallerdereparacion.R
import com.practice.tallerdereparacion.entities.Repair

class AdapterRepairs(private var list: MutableList<Repair>, private val selectRepairClickListener: (Repair) -> Unit): RecyclerView.Adapter<AdapterRepairs.ViewHolder>() {

    var listRepairs = list

   // var onItemClick: ((Repair) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemLayoutView = LayoutInflater.from(parent.context).inflate(R.layout.row_items, parent, false)
        return ViewHolder(itemLayoutView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repair = listRepairs[position]

        holder.repairId.text =  listRepairs[position].code.toString()
        holder.clientCode.text = listRepairs[position].clientCode.toString()
        holder.completionDate.text = listRepairs[position].completionDate.toString()
        holder.sparePartsUsed.text = listRepairs[position].sparePartsUsed.toString()
        holder.hoursWorked.text = listRepairs[position].hoursWorked.toString()

        holder.itemView.setOnClickListener {
            selectRepairClickListener(repair)
        }
    }

    override fun getItemCount(): Int {
        return listRepairs.size
    }

    fun setFiltered(filtered: MutableList<Repair>){
        this.listRepairs = filtered
        notifyDataSetChanged()
    }

    fun setDefaultData(){
        this.listRepairs = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var repairId: TextView
        var clientCode: TextView
        var completionDate: TextView
        var sparePartsUsed: TextView
        var hoursWorked: TextView
        init{
            repairId = itemView.findViewById(R.id.repairIdItem)
            clientCode = itemView.findViewById(R.id.clientCodeItem)
            completionDate = itemView.findViewById(R.id.completionDateItem)
            sparePartsUsed = itemView.findViewById(R.id.sparepartsUsedItem)
            hoursWorked = itemView.findViewById(R.id.hoursWorkedItem)

        }
    }

}
