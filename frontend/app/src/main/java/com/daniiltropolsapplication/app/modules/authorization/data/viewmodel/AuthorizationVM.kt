package com.daniiltropolsapplication.app.modules.authorization.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.authorization.`data`.model.AuthorizationModel
import org.koin.core.KoinComponent

class AuthorizationVM : ViewModel(), KoinComponent {
  val authorizationModel: MutableLiveData<AuthorizationModel> =
      MutableLiveData(AuthorizationModel())

  var navArguments: Bundle? = null
}
