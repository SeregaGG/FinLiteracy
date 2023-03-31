package com.daniiltropolsapplication.app.modules.passwordrecoverybadinput.`data`.model

import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class PasswordRecoveryBadInputModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtEightyNine: String? = MyApp.getInstance().resources.getString(R.string.lbl64)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtNinety: String? = MyApp.getInstance().resources.getString(R.string.msg46)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtNinetyOne: String? = MyApp.getInstance().resources.getString(R.string.msg48)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var etGroupSevenValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etGroupSixValue: String? = null
)
