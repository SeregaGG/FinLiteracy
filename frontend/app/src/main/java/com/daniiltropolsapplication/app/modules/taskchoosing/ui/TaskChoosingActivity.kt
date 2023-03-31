package com.daniiltropolsapplication.app.modules.taskchoosing.ui

import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityTaskChoosingBinding
import com.daniiltropolsapplication.app.modules.taskchoosing.`data`.viewmodel.TaskChoosingVM
import kotlin.String
import kotlin.Unit

class TaskChoosingActivity :
    BaseActivity<ActivityTaskChoosingBinding>(R.layout.activity_task_choosing) {
  private val viewModel: TaskChoosingVM by viewModels<TaskChoosingVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.taskChoosingVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "TASK_CHOOSING_ACTIVITY"

  }
}
