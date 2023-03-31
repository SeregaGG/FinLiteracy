package com.daniiltropolsapplication.app.modules.rating.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniiltropolsapplication.app.modules.rating.`data`.model.RatingModel
import com.daniiltropolsapplication.app.modules.rating.`data`.model.RatingRowModel
import com.daniiltropolsapplication.app.modules.rating.`data`.model.SpinnerGroup105Model
import com.daniiltropolsapplication.app.modules.rating.`data`.model.SpinnerGroup106Model
import kotlin.collections.MutableList
import org.koin.core.KoinComponent

class RatingVM : ViewModel(), KoinComponent {
  val ratingModel: MutableLiveData<RatingModel> = MutableLiveData(RatingModel())

  var navArguments: Bundle? = null

  val spinnerGroup106List: MutableLiveData<MutableList<SpinnerGroup106Model>> = MutableLiveData()

  val spinnerGroup105List: MutableLiveData<MutableList<SpinnerGroup105Model>> = MutableLiveData()

  val ratingList: MutableLiveData<MutableList<RatingRowModel>> = MutableLiveData(mutableListOf())
}
