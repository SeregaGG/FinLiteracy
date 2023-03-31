package com.daniiltropolsapplication.app.modules.code.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.code.`data`.model.CodeModel
import org.koin.core.KoinComponent

class CodeVM : ViewModel(), KoinComponent {
  val codeModel: MutableLiveData<CodeModel> = MutableLiveData(CodeModel())

  var navArguments: Bundle? = null
}
