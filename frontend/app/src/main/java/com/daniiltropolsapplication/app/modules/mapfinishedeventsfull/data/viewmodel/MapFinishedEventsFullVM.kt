package com.daniiltropolsapplication.app.modules.mapfinishedeventsfull.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.mapfinishedeventsfull.`data`.model.MapFinishedEventsFullModel
import org.koin.core.KoinComponent

class MapFinishedEventsFullVM : ViewModel(), KoinComponent {
  val mapFinishedEventsFullModel: MutableLiveData<MapFinishedEventsFullModel> =
      MutableLiveData(MapFinishedEventsFullModel())

  var navArguments: Bundle? = null
}
