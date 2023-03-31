package com.daniiltropolsapplication.app.modules.rightanswer.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.rightanswer.`data`.model.RightAnswerModel
import org.koin.core.KoinComponent

class RightAnswerVM : ViewModel(), KoinComponent {
  val rightAnswerModel: MutableLiveData<RightAnswerModel> = MutableLiveData(RightAnswerModel())

  var navArguments: Bundle? = null
}
