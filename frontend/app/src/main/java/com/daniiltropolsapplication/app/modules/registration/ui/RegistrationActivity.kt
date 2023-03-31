package com.daniiltropolsapplication.app.modules.registration.ui

import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityRegistrationBinding
import com.daniiltropolsapplication.app.modules.registration.`data`.viewmodel.RegistrationVM
import kotlin.String
import kotlin.Unit

class RegistrationActivity :
    BaseActivity<ActivityRegistrationBinding>(R.layout.activity_registration) {
  private val viewModel: RegistrationVM by viewModels<RegistrationVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.registrationVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "REGISTRATION_ACTIVITY"

  }
}
