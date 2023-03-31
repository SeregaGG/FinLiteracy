package com.daniiltropolsapplication.app.modules.passwordrecovery.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.passwordrecovery.`data`.model.PasswordRecoveryModel
import org.koin.core.KoinComponent

class PasswordRecoveryVM : ViewModel(), KoinComponent {
  val passwordRecoveryModel: MutableLiveData<PasswordRecoveryModel> =
      MutableLiveData(PasswordRecoveryModel())

  var navArguments: Bundle? = null
}
