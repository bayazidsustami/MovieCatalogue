package com.the_b.favoritefilm

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val filmList = MutableLiveData<ArrayList<FilmModel>>()

    companion object{
        val CONTENT_URI: Uri = Uri.parse("content://com.theb.moviecatalogue/film")
    }

    internal fun setData(context: Context){
        val dataCursor = context.contentResolver.query(
            CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val result = ArrayList<FilmModel>()

        if (dataCursor != null) {
            while (dataCursor.moveToNext()){
                val id = dataCursor.getInt(dataCursor.getColumnIndexOrThrow("id"))
                val title = dataCursor.getString(dataCursor.getColumnIndexOrThrow("title"))
                val photo = dataCursor.getString(dataCursor.getColumnIndexOrThrow("photo"))
                val overview = dataCursor.getString(dataCursor.getColumnIndexOrThrow("overview"))
                val date = dataCursor.getString(dataCursor.getColumnIndexOrThrow("date"))
                val popularity = dataCursor.getString(dataCursor.getColumnIndexOrThrow("popularity"))
                val voteAverage = dataCursor.getString(dataCursor.getColumnIndexOrThrow("voteAverage"))
                val status = dataCursor.getString(dataCursor.getColumnIndexOrThrow("status"))

                result.add(FilmModel(id, title, photo, overview, date, popularity, voteAverage, status))

            }

            filmList.postValue(result)
        }
    }

    internal fun getData(): LiveData<ArrayList<FilmModel>> {
        return filmList
    }
}