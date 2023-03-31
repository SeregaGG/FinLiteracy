package com.daniiltropolsapplication.app.modules.finaltalkpositive.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.databinding.RowListcombinedshapeThree1Binding
import com.daniiltropolsapplication.app.modules.finaltalkpositive.`data`.model.ListcombinedshapeThree1RowModel
import kotlin.Int
import kotlin.collections.List

class ListcombinedshapeThreeAdapter(
  var list: List<ListcombinedshapeThree1RowModel>
) : RecyclerView.Adapter<ListcombinedshapeThreeAdapter.RowListcombinedshapeThree1VH>() {
  private var clickListener: OnItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowListcombinedshapeThree1VH {
    val
        view=LayoutInflater.from(parent.context).inflate(R.layout.row_listcombinedshape_three1,parent,false)
    return RowListcombinedshapeThree1VH(view)
  }

  override fun onBindViewHolder(holder: RowListcombinedshapeThree1VH, position: Int) {
    val listcombinedshapeThree1RowModel = ListcombinedshapeThree1RowModel()
    // TODO uncomment following line after integration with data source
    // val listcombinedshapeThree1RowModel = list[position]
    holder.binding.listcombinedshapeThree1RowModel = listcombinedshapeThree1RowModel
  }

  override fun getItemCount(): Int = 2
  // TODO uncomment following line after integration with data source
  // return list.size

  public fun updateData(newData: List<ListcombinedshapeThree1RowModel>) {
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
      item: ListcombinedshapeThree1RowModel
    ) {
    }
  }

  inner class RowListcombinedshapeThree1VH(
    view: View
  ) : RecyclerView.ViewHolder(view) {
    val binding: RowListcombinedshapeThree1Binding =
        RowListcombinedshapeThree1Binding.bind(itemView)
  }
}
