package com.example.svetlogorskchpp.__presentation.electrical_equipment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElAssembly

class  CustomSpinnerElAssemblyAdapter  (context: Context, private val items: List<ElAssembly>) :
    ArrayAdapter<ElAssembly>(context, 0, items) {

    private val hintColor = context.getColor(R.color.text_recycle_smile)
    private val itemColor = context.getColor(R.color.text_input_assembly)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)

        val view = convertView ?:LayoutInflater.from(context).inflate(R.layout.item_spinner_voltage_title, parent, false)
        val textView = view.findViewById<TextView>(R.id.tv_voltage)

        textView.text = item?.str
        if(position == 0) textView.setTextColor(hintColor) else textView.setTextColor(itemColor)

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_spinner_voltage, parent, false)
        val textView = view.findViewById<TextView>(R.id.tv_voltage)

        if(position == 0) textView.setTextColor(hintColor) else textView.setTextColor(itemColor)

        textView.text = item?.str

        return view
    }

}