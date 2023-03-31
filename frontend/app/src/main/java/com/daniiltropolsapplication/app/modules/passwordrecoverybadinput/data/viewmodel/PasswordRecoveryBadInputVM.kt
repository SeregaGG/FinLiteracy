package com.daniiltropolsapplication.app.modules.passwordrecoverybadinput.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.passwordrecoverybadinput.`data`.model.PasswordRecoveryBadInputModel
import org.koin.core.KoinComponent

class PasswordRecoveryBadInputVM : ViewModel(), KoinComponent {
  val passwordRecoveryBadInputModel: MutableLiveData<PasswordRecoveryBadInputModel> =
      MutableLiveData(PasswordRecoveryBadInputModel())

  var navArguments: Bundle? = null
}
