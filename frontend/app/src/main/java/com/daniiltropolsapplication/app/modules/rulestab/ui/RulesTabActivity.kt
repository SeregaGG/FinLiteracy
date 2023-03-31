package com.daniiltropolsapplication.app.modules.rulestab.ui

import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityRulesTabBinding
import com.daniiltropolsapplication.app.modules.rulestab.`data`.viewmodel.RulesTabVM
import kotlin.String
import kotlin.Unit

class RulesTabActivity : BaseActivity<ActivityRulesTabBinding>(R.layout.activity_rules_tab) {
  private val viewModel: RulesTabVM by viewModels<RulesTabVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.rulesTabVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "RULES_TAB_ACTIVITY"

  }
}
