package com.daniiltropolsapplication.app.modules.timeout.ui

import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityTimeoutBinding
import com.daniiltropolsapplication.app.modules.timeout.`data`.viewmodel.TimeoutVM
import kotlin.String
import kotlin.Unit

class TimeoutActivity : BaseActivity<ActivityTimeoutBinding>(R.layout.activity_timeout) {
  private val viewModel: TimeoutVM by viewModels<TimeoutVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.timeoutVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "TIMEOUT_ACTIVITY"

  }
}
