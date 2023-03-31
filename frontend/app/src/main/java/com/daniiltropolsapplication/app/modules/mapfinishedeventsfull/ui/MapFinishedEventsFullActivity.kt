package com.daniiltropolsapplication.app.modules.mapfinishedeventsfull.ui

import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityMapFinishedEventsFullBinding
import com.daniiltropolsapplication.app.modules.mapfinishedeventsfull.`data`.viewmodel.MapFinishedEventsFullVM
import kotlin.String
import kotlin.Unit

class MapFinishedEventsFullActivity :
    BaseActivity<ActivityMapFinishedEventsFullBinding>(R.layout.activity_map_finished_events_full) {
  private val viewModel: MapFinishedEventsFullVM by viewModels<MapFinishedEventsFullVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.mapFinishedEventsFullVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "MAP_FINISHED_EVENTS_FULL_ACTIVITY"

  }
}
