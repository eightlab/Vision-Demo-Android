package co.eightlab.sample.view.id_options.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.eightlab.sample.R
import co.eightlab.sample.model.Card
import kotlinx.android.synthetic.main.row_country.view.nameTextView
import kotlinx.android.synthetic.main.row_id_options.view.*

class IdOptionListAdapter(private val dataList: List<Card>, private val selectListener: (Card) -> Unit) : RecyclerView.Adapter<IdOptionListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.row_id_options, parent, false))
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(dataList[position], selectListener)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(data: Card, selectListener: (Card) -> Unit) {
            with(itemView) {
                nameTextView.text = data.idName

                if (data.id == 1)
                    idImageView.setImageResource(R.drawable.ic_passport)
                else
                    idImageView.setImageResource(R.drawable.ic_card)

                setOnClickListener {
                    selectListener(data)
                }
            }
        }
    }
}