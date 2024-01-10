package com.example.svetlogorskchpp.electricalAssembly

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.ItemElectricalAsseblySearchBinding
import com.example.svetlogorskchpp.model.ElectricalAssemblySearch

class ElectricalAssemblySearchAdapter(private val clickListener: (idElectricAssembly: String) -> Unit):
ListAdapter<ElectricalAssemblySearch, ElectricalAssemblySearchAdapter.ElectricalAssemblySearchHolder>(
    ElectricalAssemblySearchCallback()
){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ElectricalAssemblySearchHolder {
        return ElectricalAssemblySearchHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: ElectricalAssemblySearchHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ElectricalAssemblySearchHolder(val binding: ItemElectricalAsseblySearchBinding):
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ElectricalAssemblySearchHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemElectricalAsseblySearchBinding.inflate(inflater, parent, false)
                return ElectricalAssemblySearchHolder(binding)
            }
        }

        fun bind(
            item: ElectricalAssemblySearch,
            clickListener: (idElectricAssembly: String) -> Unit) {
            binding.apply {
                textNameElectricalAssembly.text =
                    item.electricalAssembly.nameAssembly.replace("\\n", System.getProperty("line.separator"))
                textNameItemElectricalAssembly.text =
                    item.name.replace("\\n", System.getProperty("line.separator"))
                textDepartment.text = item.electricalAssembly.nameDepartment
                imageItemElectricalAssembly.setImageResource(
                    when (item.electricalAssembly.nameDepartment) {
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
                    clickListener(item.electricalAssembly.id)
                }
            }
        }
    }
}