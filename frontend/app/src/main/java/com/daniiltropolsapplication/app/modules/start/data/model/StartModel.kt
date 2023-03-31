package com.daniiltropolsapplication.app.modules.start.`data`.model

import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class StartModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguage: String? = MyApp.getInstance().resources.getString(R.string.msg)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtThirtySix: String? = MyApp.getInstance().resources.getString(R.string.lbl51)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtThirtySeven: String? = MyApp.getInstance().resources.getString(R.string.lbl52)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtThirtyEight: String? = MyApp.getInstance().resources.getString(R.string.lbl53)

)
