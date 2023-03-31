package com.daniiltropolsapplication.app.modules.rightanswer.`data`.model

import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class RightAnswerModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtFiveHundred: String? = MyApp.getInstance().resources.getString(R.string.lbl_500)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTime: String? = MyApp.getInstance().resources.getString(R.string.lbl_40_03)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtEightySix: String? = MyApp.getInstance().resources.getString(R.string.lbl26)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtFiveHundredOne: String? = MyApp.getInstance().resources.getString(R.string.lbl_5002)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtEightySeven: String? = MyApp.getInstance().resources.getString(R.string.lbl73)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguage: String? = MyApp.getInstance().resources.getString(R.string.msg45)

)
