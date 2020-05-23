package co.eightlab.sample.view.result.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.eightlab.sample.R
import co.eightlab.sample.model.ResultData
import kotlinx.android.synthetic.main.row_ocr_result.view.*

class ResultListAdapter(private val dataList: List<ResultData>) : RecyclerView.Adapter<ResultListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.row_ocr_result, parent, false))
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(resultData: ResultData) {
            with(itemView) {
                labelTextView.text = resultData?.label
                valueTextView.text = resultData?.value
            }
        }
    }
}