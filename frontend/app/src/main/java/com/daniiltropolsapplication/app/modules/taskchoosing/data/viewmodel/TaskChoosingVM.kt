package com.daniiltropolsapplication.app.modules.taskchoosing.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.taskchoosing.`data`.model.TaskChoosingModel
import org.koin.core.KoinComponent

class TaskChoosingVM : ViewModel(), KoinComponent {
  val taskChoosingModel: MutableLiveData<TaskChoosingModel> = MutableLiveData(TaskChoosingModel())

  var navArguments: Bundle? = null
}
