package com.daniiltropolsapplication.app.modules.mapfinishedevents.`data`.model

import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class MapFinishedEventsModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtFourHundred: String? = MyApp.getInstance().resources.getString(R.string.lbl_400)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTime: String? = MyApp.getInstance().resources.getString(R.string.lbl_32_54)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtGroupFiftyOne: String? = MyApp.getInstance().resources.getString(R.string.lbl60)

)
