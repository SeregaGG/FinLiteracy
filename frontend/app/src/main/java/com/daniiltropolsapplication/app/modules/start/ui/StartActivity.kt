package com.daniiltropolsapplication.app.modules.start.ui

import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityStartBinding
import com.daniiltropolsapplication.app.modules.authorization.ui.AuthorizationActivity
import com.daniiltropolsapplication.app.modules.start.`data`.viewmodel.StartVM
import kotlin.String
import kotlin.Unit

class StartActivity : BaseActivity<ActivityStartBinding>(R.layout.activity_start) {
  private val viewModel: StartVM by viewModels<StartVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.startVM = viewModel
    Handler(Looper.getMainLooper()).postDelayed( {
      val destIntent = AuthorizationActivity.getIntent(this, null)
      startActivity(destIntent)
      finish()
      }, 3000)
    }

    override fun setUpClicks(): Unit {
    }

    companion object {
      const val TAG: String = "START_ACTIVITY"

    }
  }
