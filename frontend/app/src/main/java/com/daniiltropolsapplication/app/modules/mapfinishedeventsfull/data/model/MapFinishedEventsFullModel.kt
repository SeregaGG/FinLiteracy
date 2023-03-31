package com.daniiltropolsapplication.app.modules.mapfinishedeventsfull.`data`.model

import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class MapFinishedEventsFullModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtThreeHundred: String? = MyApp.getInstance().resources.getString(R.string.lbl_300)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTime: String? = MyApp.getInstance().resources.getString(R.string.lbl_20_00)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtGroupSeventyOne: String? = MyApp.getInstance().resources.getString(R.string.lbl60)

)
