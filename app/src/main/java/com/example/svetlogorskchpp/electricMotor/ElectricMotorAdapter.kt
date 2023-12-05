package com.example.svetlogorskchpp.electricMotor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.ItemSwipeElectricMotorBinding
import com.example.svetlogorskchpp.model.electricMotor.ElectricMotor

class ElectricMotorAdapter :
    ListAdapter<ElectricMotor, ElectricMotorAdapter.ElectricMotorHolder>(ElectricMotorItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectricMotorHolder =
        ElectricMotorHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: ElectricMotorHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ElectricMotorHolder(val binding: ItemSwipeElectricMotorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): ElectricMotorHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSwipeElectricMotorBinding.inflate(layoutInflater, parent, false)
                return ElectricMotorHolder(binding)
            }
        }

        fun bind(item: ElectricMotor) {
            binding.apply {
                swipeItemBlock.apply {
                    showMode = SwipeLayout.ShowMode.LayDown
                    addDrag(SwipeLayout.DragEdge.Right, binding.swipeRightBlock)
                    addDrag(SwipeLayout.DragEdge.Left, null)
                }

                textNameBlockElectricMotor.text = item.name.replace("\\n", System.getProperty("line.separator"))
                textGeneratorDetail.text =
                    item.characteristics.replace("\\n", System.getProperty("line.separator"))
                textShemaAssembly.text = item.schema     //item.characteristics.split("\\n").last()
                textPower.text = item.p
                textTurbinDetail.text = item.pump
                textVoltage.text = itemView.context.getString(R.string.voltage, item.voltage)
                textTok.text = item.i
                textCategory.text = item.category
                textRep.visibility = if (item.rep) View.VISIBLE else View.GONE
               // imageShema.setImageResource(colorShema(item))
               // indexSection.text = item.indexSection
                //binding.nameElectricMotor.strokeColor = itemView.context.getColor(R.color.red_schema)
            }
        }

        fun colorShema(item: ElectricMotor): Int {
            return if (item.schemaState) R.drawable.baseline_circle_red else R.drawable.baseline_circle_green
        }
    }
}