package com.example.svetlogorskchpp.electricMotor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.ItemElectricMotorBinding
import com.example.svetlogorskchpp.databinding.ItemSwipeElectricMotorBinding
import com.example.svetlogorskchpp.model.electricMotor.ElectricMotor

class ElectricMotorAdapter :
    ListAdapter<ElectricMotor, ElectricMotorAdapter.ElectricMotorHolder>(
        AsyncDifferConfig.Builder(ElectricMotorItemCallback()).build()) {

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
                textGeneratorDetail.text = item.characteristics.replace("\\n", System.getProperty("line.separator"))
                textShemaAssembly.text = item.schema
                textPower.text = item.p
                textTurbinDetail.text = item.pump.replace("\\n", System.getProperty("line.separator"))
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

    class ElectricMotorHolder1(val binding: ItemElectricMotorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): ElectricMotorHolder1 {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemElectricMotorBinding.inflate(layoutInflater, parent, false)
                return ElectricMotorHolder1(binding)
            }
        }

        fun bind(item: ElectricMotor) {
            binding.apply {
                textName.text = item.name
                textView2.text = item.category
                textView3.text = item.schema
                textView4.text = item.p
                textView10.text = item.voltage
                textView11.text = item.i
            }
        }
    }
}