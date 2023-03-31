package com.daniiltropolsapplication.app.modules.rating.`data`.model

import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class RatingRowModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtOne: String? = MyApp.getInstance().resources.getString(R.string.lbl_1)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTwentyFour: String? = MyApp.getInstance().resources.getString(R.string.lbl44)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtFiveHundred: String? = MyApp.getInstance().resources.getString(R.string.lbl_500)

)
