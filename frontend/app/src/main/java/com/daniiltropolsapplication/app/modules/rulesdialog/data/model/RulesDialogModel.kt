package com.daniiltropolsapplication.app.modules.rulesdialog.`data`.model

import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class RulesDialogModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtFiveHundred: String? = MyApp.getInstance().resources.getString(R.string.lbl_500)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTime: String? = MyApp.getInstance().resources.getString(R.string.lbl_45_00)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtThirtyNine: String? = MyApp.getInstance().resources.getString(R.string.lbl54)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtForty: String? = MyApp.getInstance().resources.getString(R.string.lbl32)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguage: String? = MyApp.getInstance().resources.getString(R.string.msg19)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtFortyOne: String? = MyApp.getInstance().resources.getString(R.string.lbl33)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguageOne: String? = MyApp.getInstance().resources.getString(R.string.msg20)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtFortyTwo: String? = MyApp.getInstance().resources.getString(R.string.lbl32)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtFortyThree: String? = MyApp.getInstance().resources.getString(R.string.msg21)

)
