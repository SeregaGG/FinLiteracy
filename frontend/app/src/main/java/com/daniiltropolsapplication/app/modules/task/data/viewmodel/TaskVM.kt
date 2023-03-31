package com.daniiltropolsapplication.app.modules.task.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.task.`data`.model.TaskModel
import org.koin.core.KoinComponent

class TaskVM : ViewModel(), KoinComponent {
  val taskModel: MutableLiveData<TaskModel> = MutableLiveData(TaskModel())

  var navArguments: Bundle? = null
}
