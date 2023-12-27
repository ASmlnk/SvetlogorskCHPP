package com.example.svetlogorskchpp.electricalAssembly

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.ItemElectricalAsseblyBinding
import com.example.svetlogorskchpp.model.ElectricalAssembly
import com.example.svetlogorskchpp.model.ElectricalAssemblyFirebase

class ElectricalAssemblyAdapter(private val clickListener: (idElectricAssembly: String) -> Unit) :
    ListAdapter<ElectricalAssemblyFirebase, ElectricalAssemblyAdapter.ElectricalAssemblyHolder>(
        ElectricalAssemblyItemCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectricalAssemblyHolder =
        ElectricalAssemblyHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: ElectricalAssemblyHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ElectricalAssemblyHolder(val binding: ItemElectricalAsseblyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ElectricalAssemblyHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemElectricalAsseblyBinding.inflate(layoutInflater, parent, false)
                return ElectricalAssemblyHolder(binding)
            }
        }

        fun bind(
            item: ElectricalAssemblyFirebase,
            clickListener: (idElectricAssembly: String) -> Unit
        ) {

            binding.apply {
                textNameElectricalAssembly.text = item.nameAssembly
                textInputAssembly.text =
                    item.inputAssembly.replace("\\n", System.getProperty("line.separator"))
                textDepartment.text = item.nameDepartment
                imageItemElectricalAssembly.setImageResource(
                    when (item.nameDepartment) {
                        "ХЦ" -> R.drawable.chemistry_class_6837437
                        "КТЦ т/о" -> R.drawable.factory_1643683
                        "КТЦ к/о" -> R.drawable.water_boiler
                        "БНС" -> R.drawable.water_tank_9614228
                        "Градирня" -> R.drawable.power_plant_4491274
                        "Щит. блок" -> R.drawable.high_voltage_8107242
                        "КРУ" -> R.drawable.power_supply_283291
                        "РУ" -> R.drawable.electrical_panel_1
                        else -> R.drawable.el_panel
                    }
                )
                root.setOnClickListener {
                    clickListener(item.id)
                }
            }
        }
    }
}