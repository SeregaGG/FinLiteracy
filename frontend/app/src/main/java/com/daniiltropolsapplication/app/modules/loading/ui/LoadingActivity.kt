package com.daniiltropolsapplication.app.modules.loading.ui

import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityLoadingBinding
import com.daniiltropolsapplication.app.modules.loading.`data`.viewmodel.LoadingVM
import kotlin.String
import kotlin.Unit

class LoadingActivity : BaseActivity<ActivityLoadingBinding>(R.layout.activity_loading) {
  private val viewModel: LoadingVM by viewModels<LoadingVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.loadingVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "LOADING_ACTIVITY"

  }
}
