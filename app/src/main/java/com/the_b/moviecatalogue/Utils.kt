package com.the_b.moviecatalogue

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

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.invisible(){
    visibility = View.INVISIBLE
}