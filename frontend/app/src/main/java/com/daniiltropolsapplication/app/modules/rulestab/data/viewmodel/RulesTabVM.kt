package com.daniiltropolsapplication.app.modules.rulestab.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.rulestab.`data`.model.RulesTabModel
import org.koin.core.KoinComponent

class RulesTabVM : ViewModel(), KoinComponent {
  val rulesTabModel: MutableLiveData<RulesTabModel> = MutableLiveData(RulesTabModel())

  var navArguments: Bundle? = null
}
