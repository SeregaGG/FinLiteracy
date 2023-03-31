package com.daniiltropolsapplication.app.modules.mapfinishedevents.ui

import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityMapFinishedEventsBinding
import com.daniiltropolsapplication.app.modules.mapfinishedevents.`data`.viewmodel.MapFinishedEventsVM
import kotlin.String
import kotlin.Unit

class MapFinishedEventsActivity :
    BaseActivity<ActivityMapFinishedEventsBinding>(R.layout.activity_map_finished_events) {
  private val viewModel: MapFinishedEventsVM by viewModels<MapFinishedEventsVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.mapFinishedEventsVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "MAP_FINISHED_EVENTS_ACTIVITY"

  }
}
