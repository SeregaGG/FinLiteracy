package com.daniiltropolsapplication.app.modules.authorization.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityAuthorizationBinding
import com.daniiltropolsapplication.app.modules.authorization.`data`.viewmodel.AuthorizationVM
import kotlin.String
import kotlin.Unit

class AuthorizationActivity :
    BaseActivity<ActivityAuthorizationBinding>(R.layout.activity_authorization) {
  private val viewModel: AuthorizationVM by viewModels<AuthorizationVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.authorizationVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "AUTHORIZATION_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, AuthorizationActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
