package com.daniiltropolsapplication.app.extensions

import android.util.Patterns
import android.widget.Toast
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.di.MyApp
import kotlin.Boolean
import kotlin.String

public fun String?.isValidEmail(isMandatory: Boolean = false): Boolean {
  if (isNullOrEmpty()) {
    if (isMandatory) {
      Toast.makeText(
              MyApp.getInstance(),
              R.string.msg_please_enter_valid_email,
              Toast.LENGTH_SHORT
          ).show()
    }
    return !isMandatory
  }
  val result = 
  Patterns.EMAIL_ADDRESS.matcher(this).matches()
  if (!result) {
    Toast.makeText(
            MyApp.getInstance(),
            R.string.msg_please_enter_valid_email,
            Toast.LENGTH_SHORT
        ).show()
  }
  return result
}
