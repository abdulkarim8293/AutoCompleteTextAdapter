package com.abdulkarim.autocompletetextview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

class FromLocationAdapter(context: Context, private val fromLocationList: List<FromLocationItem>):
    ArrayAdapter<FromLocationItem>(context, 0, fromLocationList),
    Filterable {

    private var fLocationSuggestionList: List<FromLocationItem> = fromLocationList

    override fun getCount(): Int = fLocationSuggestionList.size

    override fun getItem(position: Int): FromLocationItem = fLocationSuggestionList[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val fLocationName: TextView = convertView as TextView? ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        fLocationName.text = fLocationSuggestionList[position].fLocation
        return fLocationName
    }

    @Suppress("UNCHECKED_CAST")
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                fLocationSuggestionList = filterResults.values as List<FromLocationItem>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.lowercase()

                val filterResults = FilterResults()

                filterResults.values = if (queryString==null || queryString.isEmpty())
                    fromLocationList
                else
                    fromLocationList.filter {
                        it.fLocation.lowercase().contains(queryString)
                    }
                return filterResults
            }
        }
    }

}