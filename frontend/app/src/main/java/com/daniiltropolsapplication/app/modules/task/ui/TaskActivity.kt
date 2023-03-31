package com.daniiltropolsapplication.app.modules.task.ui

import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityTaskBinding
import com.daniiltropolsapplication.app.modules.task.`data`.viewmodel.TaskVM
import kotlin.String
import kotlin.Unit

class TaskActivity : BaseActivity<ActivityTaskBinding>(R.layout.activity_task) {
  private val viewModel: TaskVM by viewModels<TaskVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.taskVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "TASK_ACTIVITY"

  }
}
