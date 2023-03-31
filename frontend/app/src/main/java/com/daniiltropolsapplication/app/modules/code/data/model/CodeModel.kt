package com.daniiltropolsapplication.app.modules.code.`data`.model

import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class CodeModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguage: String? = MyApp.getInstance().resources.getString(R.string.msg)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtFour: String? = MyApp.getInstance().resources.getString(R.string.msg_4)

)
