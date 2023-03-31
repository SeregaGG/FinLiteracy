package com.daniiltropolsapplication.app.modules.rulesdialog.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.rulesdialog.`data`.model.RulesDialogModel
import org.koin.core.KoinComponent

class RulesDialogVM : ViewModel(), KoinComponent {
  val rulesDialogModel: MutableLiveData<RulesDialogModel> = MutableLiveData(RulesDialogModel())

  var navArguments: Bundle? = null
}
