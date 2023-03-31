package com.daniiltropolsapplication.app.modules.finaltalkpositive.ui

import android.view.View
import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityFinalTalkPositiveBinding
import com.daniiltropolsapplication.app.modules.finaltalkpositive.`data`.model.ListcombinedshapeThree1RowModel
import com.daniiltropolsapplication.app.modules.finaltalkpositive.`data`.viewmodel.FinalTalkPositiveVM
import kotlin.Int
import kotlin.String
import kotlin.Unit

class FinalTalkPositiveActivity :
    BaseActivity<ActivityFinalTalkPositiveBinding>(R.layout.activity_final_talk_positive) {
  private val viewModel: FinalTalkPositiveVM by viewModels<FinalTalkPositiveVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    val listcombinedshapeThreeAdapter =
    ListcombinedshapeThreeAdapter(viewModel.listcombinedshapeThreeList.value?:mutableListOf())
    binding.recyclerListcombinedshapeThree.adapter = listcombinedshapeThreeAdapter
    listcombinedshapeThreeAdapter.setOnItemClickListener(
    object : ListcombinedshapeThreeAdapter.OnItemClickListener {
      override fun onItemClick(view:View, position:Int, item :
      ListcombinedshapeThree1RowModel) {
        onClickRecyclerListcombinedshapeThree(view, position, item)
      }
    }
    )
    viewModel.listcombinedshapeThreeList.observe(this) {
      listcombinedshapeThreeAdapter.updateData(it)
    }
    binding.finalTalkPositiveVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  fun onClickRecyclerListcombinedshapeThree(
    view: View,
    position: Int,
    item: ListcombinedshapeThree1RowModel
  ): Unit {
    when(view.id) {
    }
  }

  companion object {
    const val TAG: String = "FINAL_TALK_POSITIVE_ACTIVITY"

  }
}
