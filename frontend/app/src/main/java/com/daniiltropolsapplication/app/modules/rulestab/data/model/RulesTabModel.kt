package com.daniiltropolsapplication.app.modules.rulestab.`data`.model

import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class RulesTabModel(
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
  var txtNinetyTwo: String? = MyApp.getInstance().resources.getString(R.string.lbl54)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtNinetyThree: String? = MyApp.getInstance().resources.getString(R.string.msg49)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguage: String? = MyApp.getInstance().resources.getString(R.string.msg50)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtNinetyFour: String? = MyApp.getInstance().resources.getString(R.string.msg53)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtNinetyFive: String? = MyApp.getInstance().resources.getString(R.string.msg56)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguageOne: String? = MyApp.getInstance().resources.getString(R.string.msg60)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtNinetySix: String? = MyApp.getInstance().resources.getString(R.string.msg61)

)
