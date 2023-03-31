package com.daniiltropolsapplication.app.modules.map.ui

import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityMapBinding
import com.daniiltropolsapplication.app.modules.map.`data`.viewmodel.MapVM
import kotlin.String
import kotlin.Unit

class MapActivity : BaseActivity<ActivityMapBinding>(R.layout.activity_map) {
  private val viewModel: MapVM by viewModels<MapVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.mapVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "MAP_ACTIVITY"

  }
}
