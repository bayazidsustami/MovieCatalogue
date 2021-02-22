package com.the_b.moviecatalogue.ui.favorites.details

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.api.ApiBuilder
import com.the_b.moviecatalogue.data.db.FilmHelper
import com.the_b.moviecatalogue.data.model.local.Films
import com.the_b.moviecatalogue.widget.FavoriteWidget
import kotlinx.android.synthetic.main.activity_desc_fav_film.*
import kotlinx.android.synthetic.main.overview_layout.*

class DescFavFilm : AppCompatActivity() {

    private var film: Films? = null
    private lateinit var filmHelper: FilmHelper
    private var position: Int = 0

    companion object{
        const val EXTRA_DATA = "extra data"
        const val EXTRA_POSITION = "extra positions"
        const val REQUEST_DEL = 12
        const val RESULT_DEL = 11
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desc_fav_film)

        supportActionBar?.title = getString(R.string.details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        filmHelper = FilmHelper.getInstance(applicationContext)

        film = intent.getParcelableExtra(EXTRA_DATA)

        if (film != null){
            position = intent.getIntExtra(EXTRA_POSITION, 0)

            titleFilm.text = film?.title
            Glide.with(this).load(ApiBuilder.IMAGE_URL+film?.photo).into(imageFilm)
            releaseFilm.text = film?.date
            popularity.text = film?.popularity
            voteLabel.text = film?.voteAverage
            status.text = film?.status
            overview.text = film?.overview
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.delete_action, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete -> showAlertDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(){
        val dialogTitle: String = getString(R.string.dialog_title)
        val dialogMessage: String = getString(R.string.dialog_msg)

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder.setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes)){ _, _ ->
                val result = filmHelper.deleteById(film?.id.toString()).toLong()
                FavoriteWidget.updateWidget(this)
                if (result > 0){
                    val intent = Intent()
                    intent.putExtra(EXTRA_POSITION, position)
                    setResult(RESULT_DEL, intent)
                    finish()
                } else {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(getString(R.string.no)){dialog, _ -> dialog.cancel()}
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
