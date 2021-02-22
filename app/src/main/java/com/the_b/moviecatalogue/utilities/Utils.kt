package com.the_b.moviecatalogue.utilities

import android.view.View
import java.util.*

fun getLocale(): String {
    val locale = Locale.getDefault().language
    var language = "language"

    if (locale == "en"){
        language = "en-US"
    } else if (locale == "in"){
        language = "id-IN"
    }
    return language
}

const val TAG = "My_Tag"

fun View.visible(){
    visibility = View.VISIBLE
}
fun View.gone(){
    visibility = View.GONE
}