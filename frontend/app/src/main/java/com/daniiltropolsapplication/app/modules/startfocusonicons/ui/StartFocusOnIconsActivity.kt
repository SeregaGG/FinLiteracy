package com.daniiltropolsapplication.app.modules.startfocusonicons.ui

import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityStartFocusOnIconsBinding
import com.daniiltropolsapplication.app.modules.startfocusonicons.`data`.viewmodel.StartFocusOnIconsVM
import kotlin.String
import kotlin.Unit

class StartFocusOnIconsActivity :
    BaseActivity<ActivityStartFocusOnIconsBinding>(R.layout.activity_start_focus_on_icons) {
  private val viewModel: StartFocusOnIconsVM by viewModels<StartFocusOnIconsVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.startFocusOnIconsVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "START_FOCUS_ON_ICONS_ACTIVITY"

  }
}
