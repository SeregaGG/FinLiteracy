package com.daniiltropolsapplication.app.modules.feedback.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.feedback.`data`.model.FeedbackModel
import org.koin.core.KoinComponent

class FeedbackVM : ViewModel(), KoinComponent {
  val feedbackModel: MutableLiveData<FeedbackModel> = MutableLiveData(FeedbackModel())

  var navArguments: Bundle? = null
}
