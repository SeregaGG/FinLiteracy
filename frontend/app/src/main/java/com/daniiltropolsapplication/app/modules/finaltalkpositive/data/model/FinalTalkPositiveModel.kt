package com.daniiltropolsapplication.app.modules.finaltalkpositive.`data`.model

import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class FinalTalkPositiveModel(
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
  var txtFiftyThree: String? = MyApp.getInstance().resources.getString(R.string.lbl31)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtFiftyFour: String? = MyApp.getInstance().resources.getString(R.string.lbl32)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtFiftyFive: String? = MyApp.getInstance().resources.getString(R.string.msg26)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtFiftySix: String? = MyApp.getInstance().resources.getString(R.string.lbl33)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguage: String? = MyApp.getInstance().resources.getString(R.string.msg9)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguageOne: String? = MyApp.getInstance().resources.getString(R.string.msg10)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtFiftySeven: String? = MyApp.getInstance().resources.getString(R.string.lbl32)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtFiftyEight: String? = MyApp.getInstance().resources.getString(R.string.lbl35)

)
