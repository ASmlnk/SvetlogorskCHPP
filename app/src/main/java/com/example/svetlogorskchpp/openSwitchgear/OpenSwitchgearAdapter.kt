package com.example.svetlogorskchpp.openSwitchgear

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.databinding.ItemSwipeOpenSwichgearBinding
import com.example.svetlogorskchpp.model.powerLines.PowerLines

class OpenSwitchgearAdapter(val list: List<PowerLines>):
    RecyclerView.Adapter< OpenSwitchgearAdapter.OpenSwitchgearHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpenSwitchgearHolder =
        OpenSwitchgearHolder.inflateFrom(parent)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: OpenSwitchgearHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

     class OpenSwitchgearHolder(val binding: ItemSwipeOpenSwichgearBinding) : RecyclerView.ViewHolder(binding.root) {
         companion object{
             fun inflateFrom(parent: ViewGroup): OpenSwitchgearHolder {
                 val layoutInflater = LayoutInflater.from(parent.context)
                 val binding = ItemSwipeOpenSwichgearBinding.inflate(layoutInflater, parent, false)
                 return OpenSwitchgearHolder(binding)
             }
         }

        fun bind(item: PowerLines) {

            binding.apply {
                swipeItemOpenSwitchgear.apply {
                    showMode = SwipeLayout.ShowMode.LayDown
                    addDrag(SwipeLayout.DragEdge.Right, binding.swipeRightOpenSwitchgear)
                    addDrag(SwipeLayout.DragEdge.Left, binding.swipeLeftOpenSwitchgear)
                    addSwipeListener(object : SwipeLayout.SwipeListener {
                        override fun onStartOpen(layout: SwipeLayout?) {
                        }
                        override fun onOpen(layout: SwipeLayout?) {
                        }
                        override fun onStartClose(layout: SwipeLayout?) {
                        }
                        override fun onClose(layout: SwipeLayout?) {
                        }
                        override fun onUpdate(layout: SwipeLayout?, leftOffset: Int, topOffset: Int) {
                        }
                        override fun onHandRelease(layout: SwipeLayout?, xvel: Float, yvel: Float) {
                        }
                    })
                    textViewNamePowerLines.text = item.name
                    textViewNumberCell.text = item.numberCells.toString()
                    imageViewSh2.isVisible = item.numberSH != 1
                    imageShr1.setImageResource(keyR(item.key1SR))
                    imageShr2.setImageResource(keyR(item.key2SR))
                    imageLr.setImageResource(keyR(item.keyLR))
                    imageOr.setImageResource(keyR(item.keyOR))
                    textViewGsyApv.text = context.getString(R.string.text_gsy_apv, item.numberGHY, item.apv)
                    textApv.text = item.madeApv
                    textZemlia.text = item.earthProtection
                    textFaznai.text = item.phaseProtection
                }

            }
        }

         fun keyR(numberKey: Int): Int {
             return when(numberKey) {
                 1 -> R.drawable.key_1
                 2 -> R.drawable.key_2
                 3 -> R.drawable.key_3
                 4 -> R.drawable.key_4
                 else -> R.drawable.key_0
             }
         }
    }
}