package com.example.svetlogorskchpp.blockGeneratorTransformer

import androidx.recyclerview.widget.DiffUtil
import com.example.svetlogorskchpp.model.block.Block

class BlockItemCallback: DiffUtil.ItemCallback<Block>() {
    override fun areItemsTheSame(oldItem: Block, newItem: Block): Boolean =
        oldItem.nameBlock == newItem.nameBlock

    override fun areContentsTheSame(oldItem: Block, newItem: Block): Boolean =
        oldItem == newItem
}