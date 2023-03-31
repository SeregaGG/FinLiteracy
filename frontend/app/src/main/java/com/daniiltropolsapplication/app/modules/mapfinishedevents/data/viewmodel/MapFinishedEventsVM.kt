package com.daniiltropolsapplication.app.modules.mapfinishedevents.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.mapfinishedevents.`data`.model.MapFinishedEventsModel
import org.koin.core.KoinComponent

class MapFinishedEventsVM : ViewModel(), KoinComponent {
  val mapFinishedEventsModel: MutableLiveData<MapFinishedEventsModel> =
      MutableLiveData(MapFinishedEventsModel())

  var navArguments: Bundle? = null
}
