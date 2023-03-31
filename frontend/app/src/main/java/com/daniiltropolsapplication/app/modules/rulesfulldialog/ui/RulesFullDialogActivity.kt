package com.daniiltropolsapplication.app.modules.rulesfulldialog.ui

import android.view.View
import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityRulesFullDialogBinding
import com.daniiltropolsapplication.app.modules.rulesfulldialog.`data`.model.ListRowModel
import com.daniiltropolsapplication.app.modules.rulesfulldialog.`data`.viewmodel.RulesFullDialogVM
import kotlin.Int
import kotlin.String
import kotlin.Unit

class RulesFullDialogActivity :
    BaseActivity<ActivityRulesFullDialogBinding>(R.layout.activity_rules_full_dialog) {
  private val viewModel: RulesFullDialogVM by viewModels<RulesFullDialogVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    val listAdapter = ListAdapter(viewModel.listList.value?:mutableListOf())
    binding.recyclerList.adapter = listAdapter
    listAdapter.setOnItemClickListener(
    object : ListAdapter.OnItemClickListener {
      override fun onItemClick(view:View, position:Int, item : ListRowModel) {
        onClickRecyclerList(view, position, item)
      }
    }
    )
    viewModel.listList.observe(this) {
      listAdapter.updateData(it)
    }
    binding.rulesFullDialogVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  fun onClickRecyclerList(
    view: View,
    position: Int,
    item: ListRowModel
  ): Unit {
    when(view.id) {
    }
  }

  companion object {
    const val TAG: String = "RULES_FULL_DIALOG_ACTIVITY"

  }
}
