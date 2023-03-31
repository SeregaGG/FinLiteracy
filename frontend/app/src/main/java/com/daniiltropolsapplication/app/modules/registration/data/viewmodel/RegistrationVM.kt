package com.daniiltropolsapplication.app.modules.registration.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.registration.`data`.model.RegistrationModel
import org.koin.core.KoinComponent

class RegistrationVM : ViewModel(), KoinComponent {
  val registrationModel: MutableLiveData<RegistrationModel> = MutableLiveData(RegistrationModel())

  var navArguments: Bundle? = null
}
