package com.daniiltropolsapplication.app.modules.finaltalknegative.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.finaltalknegative.`data`.model.FinalTalkNegativeModel
import com.daniiltropolsapplication.app.modules.finaltalknegative.`data`.model.ListcombinedshapeThreeRowModel
import kotlin.collections.MutableList
import org.koin.core.KoinComponent

class FinalTalkNegativeVM : ViewModel(), KoinComponent {
  val finalTalkNegativeModel: MutableLiveData<FinalTalkNegativeModel> =
      MutableLiveData(FinalTalkNegativeModel())

  var navArguments: Bundle? = null

  val listcombinedshapeThreeList: MutableLiveData<MutableList<ListcombinedshapeThreeRowModel>> =
      MutableLiveData(mutableListOf())
}
