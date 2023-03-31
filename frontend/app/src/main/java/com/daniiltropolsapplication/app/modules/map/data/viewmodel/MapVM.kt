package com.daniiltropolsapplication.app.modules.map.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.map.`data`.model.MapModel
import org.koin.core.KoinComponent

class MapVM : ViewModel(), KoinComponent {
  val mapModel: MutableLiveData<MapModel> = MutableLiveData(MapModel())

  var navArguments: Bundle? = null
}
