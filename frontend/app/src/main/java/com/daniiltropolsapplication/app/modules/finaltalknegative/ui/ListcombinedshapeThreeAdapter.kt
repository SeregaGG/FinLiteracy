package com.daniiltropolsapplication.app.modules.finaltalknegative.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.databinding.RowListcombinedshapeThreeBinding
import com.daniiltropolsapplication.app.modules.finaltalknegative.`data`.model.ListcombinedshapeThreeRowModel
import kotlin.Int
import kotlin.collections.List

class ListcombinedshapeThreeAdapter(
  var list: List<ListcombinedshapeThreeRowModel>
) : RecyclerView.Adapter<ListcombinedshapeThreeAdapter.RowListcombinedshapeThreeVH>() {
  private var clickListener: OnItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowListcombinedshapeThreeVH {
    val
        view=LayoutInflater.from(parent.context).inflate(R.layout.row_listcombinedshape_three,parent,false)
    return RowListcombinedshapeThreeVH(view)
  }

  override fun onBindViewHolder(holder: RowListcombinedshapeThreeVH, position: Int) {
    val listcombinedshapeThreeRowModel = ListcombinedshapeThreeRowModel()
    // TODO uncomment following line after integration with data source
    // val listcombinedshapeThreeRowModel = list[position]
    holder.binding.listcombinedshapeThreeRowModel = listcombinedshapeThreeRowModel
  }

  override fun getItemCount(): Int = 2
  // TODO uncomment following line after integration with data source
  // return list.size

  public fun updateData(newData: List<ListcombinedshapeThreeRowModel>) {
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
      item: ListcombinedshapeThreeRowModel
    ) {
    }
  }

  inner class RowListcombinedshapeThreeVH(
    view: View
  ) : RecyclerView.ViewHolder(view) {
    val binding: RowListcombinedshapeThreeBinding = RowListcombinedshapeThreeBinding.bind(itemView)
  }
}
