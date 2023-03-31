package com.daniiltropolsapplication.app.modules.registration.`data`.model

import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class RegistrationModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtThree: String? = MyApp.getInstance().resources.getString(R.string.lbl20)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguage: String? = MyApp.getInstance().resources.getString(R.string.msg4)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var etGroupTwentyOneValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etGroupTwentyTwoValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etGroupTwentyValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etGroupSeventeenValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etGroupNineteenValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etGroupEighteenValue: String? = null
)
