package com.daniiltropolsapplication.app.modules.finaltalkpositive.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.finaltalkpositive.`data`.model.FinalTalkPositiveModel
import com.daniiltropolsapplication.app.modules.finaltalkpositive.`data`.model.ListcombinedshapeThree1RowModel
import kotlin.collections.MutableList
import org.koin.core.KoinComponent

class FinalTalkPositiveVM : ViewModel(), KoinComponent {
  val finalTalkPositiveModel: MutableLiveData<FinalTalkPositiveModel> =
      MutableLiveData(FinalTalkPositiveModel())

  var navArguments: Bundle? = null

  val listcombinedshapeThreeList: MutableLiveData<MutableList<ListcombinedshapeThree1RowModel>> =
      MutableLiveData(mutableListOf())
}
