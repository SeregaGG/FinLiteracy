package com.daniiltropolsapplication.app.modules.finaltalknegative.ui

import android.view.View
import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityFinalTalkNegativeBinding
import com.daniiltropolsapplication.app.modules.finaltalknegative.`data`.model.ListcombinedshapeThreeRowModel
import com.daniiltropolsapplication.app.modules.finaltalknegative.`data`.viewmodel.FinalTalkNegativeVM
import kotlin.Int
import kotlin.String
import kotlin.Unit

class FinalTalkNegativeActivity :
    BaseActivity<ActivityFinalTalkNegativeBinding>(R.layout.activity_final_talk_negative) {
  private val viewModel: FinalTalkNegativeVM by viewModels<FinalTalkNegativeVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    val listcombinedshapeThreeAdapter =
    ListcombinedshapeThreeAdapter(viewModel.listcombinedshapeThreeList.value?:mutableListOf())
    binding.recyclerListcombinedshapeThree.adapter = listcombinedshapeThreeAdapter
    listcombinedshapeThreeAdapter.setOnItemClickListener(
    object : ListcombinedshapeThreeAdapter.OnItemClickListener {
      override fun onItemClick(view:View, position:Int, item :
      ListcombinedshapeThreeRowModel) {
        onClickRecyclerListcombinedshapeThree(view, position, item)
      }
    }
    )
    viewModel.listcombinedshapeThreeList.observe(this) {
      listcombinedshapeThreeAdapter.updateData(it)
    }
    binding.finalTalkNegativeVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  fun onClickRecyclerListcombinedshapeThree(
    view: View,
    position: Int,
    item: ListcombinedshapeThreeRowModel
  ): Unit {
    when(view.id) {
    }
  }

  companion object {
    const val TAG: String = "FINAL_TALK_NEGATIVE_ACTIVITY"

  }
}
