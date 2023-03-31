package com.daniiltropolsapplication.app.modules.start.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.start.`data`.model.StartModel
import org.koin.core.KoinComponent

class StartVM : ViewModel(), KoinComponent {
  val startModel: MutableLiveData<StartModel> = MutableLiveData(StartModel())

  var navArguments: Bundle? = null
}
