package com.daniiltropolsapplication.app.modules.finish.ui

import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityFinishBinding
import com.daniiltropolsapplication.app.modules.finish.`data`.viewmodel.FinishVM
import kotlin.String
import kotlin.Unit

class FinishActivity : BaseActivity<ActivityFinishBinding>(R.layout.activity_finish) {
  private val viewModel: FinishVM by viewModels<FinishVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.finishVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "FINISH_ACTIVITY"

  }
}
