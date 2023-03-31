package com.daniiltropolsapplication.app.modules.startfocusonicons.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.startfocusonicons.`data`.model.StartFocusOnIconsModel
import org.koin.core.KoinComponent

class StartFocusOnIconsVM : ViewModel(), KoinComponent {
  val startFocusOnIconsModel: MutableLiveData<StartFocusOnIconsModel> =
      MutableLiveData(StartFocusOnIconsModel())

  var navArguments: Bundle? = null
}
