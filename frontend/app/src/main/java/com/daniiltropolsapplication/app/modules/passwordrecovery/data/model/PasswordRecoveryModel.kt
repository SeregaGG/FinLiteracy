package com.daniiltropolsapplication.app.modules.passwordrecovery.`data`.model

import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class PasswordRecoveryModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtFortyNine: String? = MyApp.getInstance().resources.getString(R.string.lbl64)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtEma: String? = MyApp.getInstance().resources.getString(R.string.msg_e_ma)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var etGroupFortyEightValue: String? = null
)
