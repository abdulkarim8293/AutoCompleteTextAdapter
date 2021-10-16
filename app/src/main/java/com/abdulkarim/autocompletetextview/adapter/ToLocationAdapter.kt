package com.abdulkarim.autocompletetextview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

class ToLocationAdapter(context: Context, private val toLocationList: List<String>):
    ArrayAdapter<String>(context, 0, toLocationList),
    Filterable {
    private var tLocationSuggestionList: List<String> = toLocationList

    override fun getCount(): Int = tLocationSuggestionList.size

    override fun getItem(position: Int): String = tLocationSuggestionList[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val tLocationName: TextView = convertView as TextView? ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        tLocationName.text = tLocationSuggestionList[position]
        return tLocationName
    }

    @Suppress("UNCHECKED_CAST")
    override fun getFilter(): Filter {

        return object : Filter() {

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                tLocationSuggestionList = filterResults.values as List<String>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.lowercase()

                val filterResults = FilterResults()

                filterResults.values = if (queryString==null || queryString.isEmpty())
                    toLocationList
                else
                    toLocationList.filter {
                        it.lowercase().contains(queryString)
                    }
                return filterResults
            }
        }
    }
}