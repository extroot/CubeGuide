package ru.extroot.newcubeguide

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerAdapter(
    private val titleData: List<String?>,
    private val imageData: List<Int>,
    private val algData: List<String>,
    private val isCounting: Boolean = false
) : RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.algorithm_line_image)
        val formulaTextView: TextView = itemView.findViewById(R.id.algorithm_line_alg)
        val titleTextView: TextView = itemView.findViewById(R.id.algorithm_line_title)
        val dividerView: View = itemView.findViewById(R.id.divider)
        val countingTextView: TextView = itemView.findViewById(R.id.algorithm_line_countingText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.formulaTextView.text = algData[position]

        if (isCounting) {
            holder.countingTextView.text = (position + 1).toString()
        } else {
            holder.countingTextView.visibility = View.GONE
        }

        if (titleData[position] != null) {
            holder.titleTextView.text = titleData[position]
            holder.dividerView.visibility = View.GONE
        } else {
            holder.titleTextView.visibility = View.GONE
        }
        if (position == 0) {
            holder.dividerView.visibility = View.GONE
        }
        holder.imageView.setImageResource(imageData[position])
    }

    override fun getItemCount(): Int {
        return algData.size
    }
}