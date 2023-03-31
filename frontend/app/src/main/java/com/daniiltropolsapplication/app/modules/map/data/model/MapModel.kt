package com.daniiltropolsapplication.app.modules.map.`data`.model

import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class MapModel(
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
  var txtGroupThirtyFive: String? = MyApp.getInstance().resources.getString(R.string.lbl60)

)
