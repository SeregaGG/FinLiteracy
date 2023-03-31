package com.daniiltropolsapplication.app.modules.finish.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.finish.`data`.model.FinishModel
import org.koin.core.KoinComponent

class FinishVM : ViewModel(), KoinComponent {
  val finishModel: MutableLiveData<FinishModel> = MutableLiveData(FinishModel())

  var navArguments: Bundle? = null
}
