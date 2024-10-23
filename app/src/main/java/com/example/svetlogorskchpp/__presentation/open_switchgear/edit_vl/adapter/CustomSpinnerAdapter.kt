package com.example.svetlogorskchpp.__presentation.open_switchgear.edit_vl.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.KeyOry

class CustomSpinnerAdapter(context: Context, private val items: List<KeyOry>) :
    ArrayAdapter<KeyOry>(context, R.layout.item_spinner_key_ory, items) {

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_spinner_key_ory, parent, false)
        val image = view.findViewById<ImageView>(R.id.iv_key)
        image.setImageDrawable(when(getItem(position)) {
            KeyOry.KEY_0 ->context.getDrawable(R.drawable.key_0)
            KeyOry.KEY_1 ->context.getDrawable(R.drawable.key_1)
            KeyOry.KEY_2 ->context.getDrawable(R.drawable.key_2)
            KeyOry.KEY_3 ->context.getDrawable(R.drawable.key_3)
            KeyOry.KEY_4 ->context.getDrawable(R.drawable.key_4)
            null -> TODO()
        })
        return view
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_spinner_key_ory, parent, false)

        val image = view.findViewById<ImageView>(R.id.iv_key)
        image.setImageDrawable(when(getItem(position)) {
            KeyOry.KEY_0 ->context.getDrawable(R.drawable.key_0)
            KeyOry.KEY_1 ->context.getDrawable(R.drawable.key_1)
            KeyOry.KEY_2 ->context.getDrawable(R.drawable.key_2)
            KeyOry.KEY_3 ->context.getDrawable(R.drawable.key_3)
            KeyOry.KEY_4 ->context.getDrawable(R.drawable.key_4)
            null -> TODO()
        })
        return view
    }
}