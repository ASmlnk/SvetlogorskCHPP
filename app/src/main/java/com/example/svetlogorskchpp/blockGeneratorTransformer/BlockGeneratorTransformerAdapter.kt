package com.example.svetlogorskchpp.blockGeneratorTransformer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.example.svetlogorskchpp.databinding.ItemSwipeBlockGeneratorBinding
import com.example.svetlogorskchpp.model.block.Block

class BlockGeneratorTransformerAdapter :
    ListAdapter<Block, BlockGeneratorTransformerAdapter.BlockGeneratorTransformerHolder>(
        BlockItemCallback()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BlockGeneratorTransformerHolder =
        BlockGeneratorTransformerHolder.inflaterFrom(parent) {
            val list = currentList
            val item = list[it]
            item.expanded = !item.expanded
            notifyItemChanged(it)
        }

    override fun onBindViewHolder(holder: BlockGeneratorTransformerHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class BlockGeneratorTransformerHolder(
        val binding: ItemSwipeBlockGeneratorBinding,
        val onClickPosition: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflaterFrom(
                parent: ViewGroup,
                onClickPosition: (Int) -> Unit
            ): BlockGeneratorTransformerHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSwipeBlockGeneratorBinding.inflate(layoutInflater, parent, false)
                return BlockGeneratorTransformerHolder(binding, onClickPosition)
            }
        }

        fun bind(item: Block) {

            binding.apply {
                swipeItemBlock.apply {
                    showMode = SwipeLayout.ShowMode.PullOut
                    addDrag(SwipeLayout.DragEdge.Right, binding.swipeRightBlock)
                    addDrag(SwipeLayout.DragEdge.Left, binding.swipeLeftBlock)
                }
                swipeCenterBlock.setOnClickListener {
                    onClickPosition(absoluteAdapterPosition)
                }
                textViewNameBlock.text = item.nameBlock
                textViewTransformator.text = item.nameTransformer
                textViewGenerator.text = item.nameGenerator
                textViewTurbin.text = item.nameTurbin
                textGeneratorDetail.text = item.detailGenerator
                textTransformatorDetail.text = item.detailTransformer
                textTurbinDetail.text = item.detailTurbin
                textHiddenGenerator.text = item.decodingGenerator
                textHiddenTransformator.text = item.decodingTransformer
                textHiddenTurbin.text = item.decodingTurbin
            }
            if (item.expanded) {
                binding.textHidden.visibility = View.VISIBLE
            } else {
                binding.textHidden.visibility = View.GONE
            }
        }
    }
}