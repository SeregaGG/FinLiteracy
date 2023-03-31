package com.daniiltropolsapplication.app.modules.rating.ui

import android.view.View
import androidx.activity.viewModels
import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.base.BaseActivity
import com.daniiltropolsapplication.app.databinding.ActivityRatingBinding
import com.daniiltropolsapplication.app.modules.rating.`data`.model.RatingRowModel
import com.daniiltropolsapplication.app.modules.rating.`data`.model.SpinnerGroup105Model
import com.daniiltropolsapplication.app.modules.rating.`data`.model.SpinnerGroup106Model
import com.daniiltropolsapplication.app.modules.rating.`data`.viewmodel.RatingVM
import kotlin.Int
import kotlin.String
import kotlin.Unit

class RatingActivity : BaseActivity<ActivityRatingBinding>(R.layout.activity_rating) {
  private val viewModel: RatingVM by viewModels<RatingVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    viewModel.spinnerGroup106List.value = mutableListOf(
    SpinnerGroup106Model("Item1"),
    SpinnerGroup106Model("Item2"),
    SpinnerGroup106Model("Item3"),
    SpinnerGroup106Model("Item4"),
    SpinnerGroup106Model("Item5")
    )
    val spinnerGroup106Adapter =
    SpinnerGroup106Adapter(this,R.layout.spinner_item,viewModel.spinnerGroup106List.value?:
    mutableListOf())
    binding.spinnerGroup106.adapter = spinnerGroup106Adapter
    viewModel.spinnerGroup105List.value = mutableListOf(
    SpinnerGroup105Model("Item1"),
    SpinnerGroup105Model("Item2"),
    SpinnerGroup105Model("Item3"),
    SpinnerGroup105Model("Item4"),
    SpinnerGroup105Model("Item5")
    )
    val spinnerGroup105Adapter =
    SpinnerGroup105Adapter(this,R.layout.spinner_item,viewModel.spinnerGroup105List.value?:
    mutableListOf())
    binding.spinnerGroup105.adapter = spinnerGroup105Adapter
    val ratingAdapter = RatingAdapter(viewModel.ratingList.value?:mutableListOf())
    binding.recyclerRating.adapter = ratingAdapter
    ratingAdapter.setOnItemClickListener(
    object : RatingAdapter.OnItemClickListener {
      override fun onItemClick(view:View, position:Int, item : RatingRowModel) {
        onClickRecyclerRating(view, position, item)
      }
    }
    )
    viewModel.ratingList.observe(this) {
      ratingAdapter.updateData(it)
    }
    binding.ratingVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  fun onClickRecyclerRating(
    view: View,
    position: Int,
    item: RatingRowModel
  ): Unit {
    when(view.id) {
    }
  }

  companion object {
    const val TAG: String = "RATING_ACTIVITY"

  }
}
