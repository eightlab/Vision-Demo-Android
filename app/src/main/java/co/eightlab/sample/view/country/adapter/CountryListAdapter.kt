package co.eightlab.sample.view.country.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import co.eightlab.sample.R
import co.eightlab.sample.model.CountryData
import kotlinx.android.synthetic.main.row_country.view.*

/**
 * Created by surensth on 5/22/20.
 */

class CountryListAdapter(private val dataList: List<CountryData>, private val selectListener: (CountryData) -> Unit) : RecyclerView.Adapter<CountryListAdapter.ViewHolder>(), Filterable {
    private var countryListFiltered: MutableList<CountryData> = ArrayList()

    init {
        countryListFiltered.addAll(dataList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.row_country, parent, false))
    }

    override fun getItemCount(): Int = countryListFiltered.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(countryListFiltered[position], selectListener)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(data: CountryData, selectListener: (CountryData) -> Unit) {
            with(itemView) {
                nameTextView.text = data?.name

                setOnClickListener {
                    selectListener(data)
                }
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSearch: CharSequence?): FilterResults {
                if (charSearch?.isNotEmpty() == true) {
                    val resultList = ArrayList<CountryData>()
                    for (data in dataList) {
                        if (data.name!!.toLowerCase().contains(charSearch.toString().toLowerCase())) {
                            resultList.add(data)
                        }
                    }
                    countryListFiltered = resultList

                } else {
                    countryListFiltered.clear()
                    countryListFiltered.addAll(dataList)
                }

                val filterResults = FilterResults()
                filterResults.values = countryListFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryListFiltered = results?.values as ArrayList<CountryData>
                notifyDataSetChanged()
            }
        }
    }
}