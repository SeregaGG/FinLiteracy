package com.daniiltropolsapplication.app.modules.timeout.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.timeout.`data`.model.TimeoutModel
import org.koin.core.KoinComponent

class TimeoutVM : ViewModel(), KoinComponent {
  val timeoutModel: MutableLiveData<TimeoutModel> = MutableLiveData(TimeoutModel())

  var navArguments: Bundle? = null
}
