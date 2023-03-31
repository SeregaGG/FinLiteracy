package com.daniiltropolsapplication.app.modules.rating.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.databinding.RowRatingBinding
import com.daniiltropolsapplication.app.modules.rating.`data`.model.RatingRowModel
import kotlin.Int
import kotlin.collections.List

class RatingAdapter(
  var list: List<RatingRowModel>
) : RecyclerView.Adapter<RatingAdapter.RowRatingVH>() {
  private var clickListener: OnItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowRatingVH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_rating,parent,false)
    return RowRatingVH(view)
  }

  override fun onBindViewHolder(holder: RowRatingVH, position: Int) {
    val ratingRowModel = RatingRowModel()
    // TODO uncomment following line after integration with data source
    // val ratingRowModel = list[position]
    holder.binding.ratingRowModel = ratingRowModel
  }

  override fun getItemCount(): Int = 4
  // TODO uncomment following line after integration with data source
  // return list.size

  public fun updateData(newData: List<RatingRowModel>) {
    list = newData
    notifyDataSetChanged()
  }

  fun setOnItemClickListener(clickListener: OnItemClickListener) {
    this.clickListener = clickListener
  }

  interface OnItemClickListener {
    fun onItemClick(
      view: View,
      position: Int,
      item: RatingRowModel
    ) {
    }
  }

  inner class RowRatingVH(
    view: View
  ) : RecyclerView.ViewHolder(view) {
    val binding: RowRatingBinding = RowRatingBinding.bind(itemView)
  }
}
