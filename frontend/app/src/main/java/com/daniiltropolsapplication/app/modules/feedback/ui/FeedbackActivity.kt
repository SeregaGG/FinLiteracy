package com.daniiltropolsapplication.app.modules.feedback.ui

import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityFeedbackBinding
import com.daniiltropolsapplication.app.modules.feedback.`data`.viewmodel.FeedbackVM
import kotlin.String
import kotlin.Unit

class FeedbackActivity : BaseActivity<ActivityFeedbackBinding>(R.layout.activity_feedback) {
  private val viewModel: FeedbackVM by viewModels<FeedbackVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.feedbackVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "FEEDBACK_ACTIVITY"

  }
}
