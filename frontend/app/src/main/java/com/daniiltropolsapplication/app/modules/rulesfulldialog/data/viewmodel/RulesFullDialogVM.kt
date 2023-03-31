package com.daniiltropolsapplication.app.modules.rulesfulldialog.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.rulesfulldialog.`data`.model.ListRowModel
import com.daniiltropolsapplication.app.modules.rulesfulldialog.`data`.model.RulesFullDialogModel
import kotlin.collections.MutableList
import org.koin.core.KoinComponent

class RulesFullDialogVM : ViewModel(), KoinComponent {
  val rulesFullDialogModel: MutableLiveData<RulesFullDialogModel> =
      MutableLiveData(RulesFullDialogModel())

  var navArguments: Bundle? = null

  val listList: MutableLiveData<MutableList<ListRowModel>> = MutableLiveData(mutableListOf())
}
