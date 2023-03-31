package com.daniiltropolsapplication.app.modules.rightanswer.ui

import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityRightAnswerBinding
import com.daniiltropolsapplication.app.modules.rightanswer.`data`.viewmodel.RightAnswerVM
import kotlin.String
import kotlin.Unit

class RightAnswerActivity : BaseActivity<ActivityRightAnswerBinding>(R.layout.activity_right_answer)
    {
  private val viewModel: RightAnswerVM by viewModels<RightAnswerVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.rightAnswerVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "RIGHT_ANSWER_ACTIVITY"

  }
}
