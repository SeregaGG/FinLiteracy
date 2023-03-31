package com.daniiltropolsapplication.app.modules.rulesdialog.ui

import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityRulesDialogBinding
import com.daniiltropolsapplication.app.modules.rulesdialog.`data`.viewmodel.RulesDialogVM
import kotlin.String
import kotlin.Unit

class RulesDialogActivity : BaseActivity<ActivityRulesDialogBinding>(R.layout.activity_rules_dialog)
    {
  private val viewModel: RulesDialogVM by viewModels<RulesDialogVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.rulesDialogVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "RULES_DIALOG_ACTIVITY"

  }
}
