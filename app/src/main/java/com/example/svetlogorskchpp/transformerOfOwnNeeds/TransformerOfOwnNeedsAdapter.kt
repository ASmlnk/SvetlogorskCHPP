package com.example.svetlogorskchpp.transformerOfOwnNeeds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.ItemSwipeTsnBinding
import com.example.svetlogorskchpp.model.transformerNeeds.Tsn


class TransformerOfOwnNeedsAdapter :
    ListAdapter<Tsn, TransformerOfOwnNeedsAdapter.TransformerOfOwnNeedsHolder>(TsnItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransformerOfOwnNeedsHolder =
        TransformerOfOwnNeedsHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: TransformerOfOwnNeedsHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class TransformerOfOwnNeedsHolder(val binding: ItemSwipeTsnBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): TransformerOfOwnNeedsHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSwipeTsnBinding.inflate(layoutInflater, parent, false)
                return TransformerOfOwnNeedsHolder(binding)
            }
        }

        fun bind(item: Tsn) {
            binding.apply {
                swipeItemTsn.apply {
                    showMode = SwipeLayout.ShowMode.LayDown
                    addDrag(SwipeLayout.DragEdge.Right, binding.swipeItemTsnRight)
                    addDrag(SwipeLayout.DragEdge.Left, binding.swipeItemTsnLeft)
                }
                textNameTsn.text = item.nameTsn
                textTypeTr.text = item.typeTsn
                textPowerTr.text = item.powerTsn
                textHighVolt.text = itemView.context.getString(R.string.tex_volt_tsn, item.highVoltage, item.highVoltageCell)
                textLowVolt.text = itemView.context.getString(R.string.tex_volt_tsn, item.lowVoltage, item.lowVoltageCell)
                textControlPanel.text = itemView.context.getString(R.string.tex_volt_tsn, item.controlPanel, item.controlPanelCell)
                textShutdownProtection.text = item.shutdownProtection
                textSignalProtection.text = item.signalProtection
            }
        }
    }
}