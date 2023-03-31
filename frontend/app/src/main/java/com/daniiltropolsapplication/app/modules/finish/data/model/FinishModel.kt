package com.daniiltropolsapplication.app.modules.finish.`data`.model

import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class FinishModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtThreeHundred: String? = MyApp.getInstance().resources.getString(R.string.lbl_300)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTime: String? = MyApp.getInstance().resources.getString(R.string.lbl_00_00)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtFortySix: String? = MyApp.getInstance().resources.getString(R.string.lbl62)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtThreeHundredOne: String? = MyApp.getInstance().resources.getString(R.string.lbl_3002)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtFortySeven: String? = MyApp.getInstance().resources.getString(R.string.msg25)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtFortyEight: String? = MyApp.getInstance().resources.getString(R.string.lbl35)

)
