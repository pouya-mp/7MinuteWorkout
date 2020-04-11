package com.pouyaa.a7minuteworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history_row.view.*

class HistoryAdapter(val context: Context, val items : ArrayList<String>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(view : View): RecyclerView.ViewHolder(view){

        val llHistoryItem = view.llHistoryItem
        val tvIndex = view.tvIndex
        val tvDate = view.tvDate

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_history_row, parent , false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date : String = items.get(position)

        holder.tvIndex.text = (position + 1).toString()
        holder.tvDate.text = date

        if (position % 2 == 0){
            holder.llHistoryItem.setBackgroundColor(Color.parseColor(context.getString(R.string.colorRecycleviewHistoryEvenBackground)))
        }else{
            holder.llHistoryItem.setBackgroundColor(Color.parseColor(context.getString(R.string.colorRecycleviewHistoryOddBackground)))

        }

    }


}