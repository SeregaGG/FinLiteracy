package com.daniiltropolsapplication.app.modules.passwordrecoverybadinput.ui

import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityPasswordRecoveryBadInputBinding
import com.daniiltropolsapplication.app.modules.passwordrecoverybadinput.`data`.viewmodel.PasswordRecoveryBadInputVM
import kotlin.String
import kotlin.Unit

class PasswordRecoveryBadInputActivity :
    BaseActivity<ActivityPasswordRecoveryBadInputBinding>(R.layout.activity_password_recovery_bad_input)
    {
  private val viewModel: PasswordRecoveryBadInputVM by viewModels<PasswordRecoveryBadInputVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.passwordRecoveryBadInputVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "PASSWORD_RECOVERY_BAD_INPUT_ACTIVITY"

  }
}
