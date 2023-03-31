package com.daniiltropolsapplication.app.modules.rating.`data`.model

import com.daniiltropolsapplication.app.R
import com.daniiltropolsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class RatingModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtThreeHundred: String? = MyApp.getInstance().resources.getString(R.string.lbl_300)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTime: String? = MyApp.getInstance().resources.getString(R.string.lbl_00_00)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTwentyEight: String? = MyApp.getInstance().resources.getString(R.string.lbl40)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTwentyNine: String? = MyApp.getInstance().resources.getString(R.string.lbl41)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtThirty: String? = MyApp.getInstance().resources.getString(R.string.lbl42)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtThirtyOne: String? = MyApp.getInstance().resources.getString(R.string.lbl43)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtThirtyTwo: String? = MyApp.getInstance().resources.getString(R.string.lbl48)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTwenty: String? = MyApp.getInstance().resources.getString(R.string.lbl_202)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtThirtyThree: String? = MyApp.getInstance().resources.getString(R.string.msg18)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguage: String? = MyApp.getInstance().resources.getString(R.string.lbl_0)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtThirtyFour: String? = MyApp.getInstance().resources.getString(R.string.lbl49)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtThirtyFive: String? = MyApp.getInstance().resources.getString(R.string.lbl50)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var etTableHeadValue: String? = null
)
