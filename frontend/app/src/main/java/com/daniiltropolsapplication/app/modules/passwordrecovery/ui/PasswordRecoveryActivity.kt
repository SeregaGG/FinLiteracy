package com.daniiltropolsapplication.app.modules.passwordrecovery.ui

import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityPasswordRecoveryBinding
import com.daniiltropolsapplication.app.modules.passwordrecovery.`data`.viewmodel.PasswordRecoveryVM
import kotlin.String
import kotlin.Unit

class PasswordRecoveryActivity :
    BaseActivity<ActivityPasswordRecoveryBinding>(R.layout.activity_password_recovery) {
  private val viewModel: PasswordRecoveryVM by viewModels<PasswordRecoveryVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.passwordRecoveryVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "PASSWORD_RECOVERY_ACTIVITY"

  }
}
