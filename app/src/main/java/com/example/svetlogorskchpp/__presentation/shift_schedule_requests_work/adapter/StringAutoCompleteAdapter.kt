package com.example.svetlogorskchpp.__presentation.shift_schedule_requests_work.adapter

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__presentation.shift_schedule_edit_composition.model.Staff

class StringAutoCompleteAdapter(
    private val context: Context,
    private val suggestions: List<String> = emptyList(),
) :
    ArrayAdapter<String>(context, 0, suggestions) {

    private var filteredSuggestions: MutableList<String> = suggestions.toMutableList()


    override fun getCount(): Int = filteredSuggestions.size

    override fun getItem(position: Int): String = filteredSuggestions[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val suggestion = getItem(position)
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_request_work_edit_text, parent, false)
        val textViewSuggestion = view.findViewById<TextView>(R.id.text_composition)
        textViewSuggestion.text = suggestion
        return view
    }

    fun submitList(listUpdate: List<String>) {
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
                        it.contains(constraint, ignoreCase = true)
                    }
                }
                results.values = filteredList
                results.count = filteredList.size
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredSuggestions.clear()
                filteredSuggestions.addAll(results?.values as List<String>)
                notifyDataSetChanged()
            }
        }
    }
}