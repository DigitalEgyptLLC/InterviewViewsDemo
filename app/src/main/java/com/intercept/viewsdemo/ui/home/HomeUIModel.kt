package com.intercept.viewsdemo.ui.home

import android.content.Context
import com.intercept.viewsdemo.R
import com.intercept.viewsdemo.data.HomeDataModel

data class HomeUIModel(val titlePrefix: String, val imageUrl: String)

fun HomeDataModel.toUIModel(context: Context): HomeUIModel {
    val title = context.getString(R.string.home_item_title_prefix)
    return HomeUIModel(imageUrl = imageUrl, titlePrefix = title)
}