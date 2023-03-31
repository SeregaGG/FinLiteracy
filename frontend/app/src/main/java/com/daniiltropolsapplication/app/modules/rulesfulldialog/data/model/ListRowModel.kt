package com.daniiltropolsapplication.app.modules.rulesfulldialog.`data`.model

import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class ListRowModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtFiftyNine: String? = MyApp.getInstance().resources.getString(R.string.msg44)

)
