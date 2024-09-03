package com.example.svetlogorskchpp.__presentation.shift_schedule_edit_composition.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__presentation.shift_schedule_edit_composition.model.Staff

class StaffListAdapter(private val context: Context, private val suggestions: List<Staff>) :
    ArrayAdapter<Staff>(context, 0, suggestions) {

    private var filteredSuggestions: MutableList<Staff> = suggestions.toMutableList()

    override fun getCount(): Int = filteredSuggestions.size

    override fun getItem(position: Int): Staff = filteredSuggestions[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val suggestion = getItem(position) as Staff
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_shift_schedule_edit_text, parent, false)
        val textViewSuggestion = view.findViewById<TextView>(R.id.text_composition)
        textViewSuggestion.text = suggestion.name
        return view
    }

    fun submitList(listUpdate: List<Staff>) {
        filteredSuggestions.clear()
        filteredSuggestions.addAll(listUpdate)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                val filteredList = if (constraint.isNullOrEmpty()) {
                    suggestions
                } else {
                    suggestions.filter {
                        it.name.contains(constraint, ignoreCase = true)
                    }
                }
                results.values = filteredList
                results.count = filteredList.size
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredSuggestions.clear()
                filteredSuggestions.addAll(results?.values as List<Staff>)
                notifyDataSetChanged()
            }
        }
    }
}
