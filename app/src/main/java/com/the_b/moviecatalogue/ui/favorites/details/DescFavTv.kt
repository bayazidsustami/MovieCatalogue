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
import com.the_b.moviecatalogue.data.db.TvShowHelper
import com.the_b.moviecatalogue.data.model.local.TvShows
import kotlinx.android.synthetic.main.activity_desc_fav_tv.*
import kotlinx.android.synthetic.main.overview_layout.*

class DescFavTv : AppCompatActivity() {

    private var tvShow: TvShows? = null
    private lateinit var tvHelper: TvShowHelper
    private var position: Int = 0

    companion object{
        const val EXTRA_DATA = "extra data"
        const val EXTRA_POSITION = "extra position"
        const val REQUEST_DEL_TV = 13
        const val RESULT_DEL_TV = 14
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desc_fav_tv)

        supportActionBar?.title = getString(R.string.details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvHelper = TvShowHelper.getInstance(applicationContext)

        tvShow = intent.getParcelableExtra(EXTRA_DATA)

        if (tvShow != null){
            position = intent.getIntExtra(EXTRA_POSITION, 0)

            Glide.with(this).load(ApiBuilder.IMAGE_URL+tvShow?.photo).into(imageFilm)
            titleFilm.text = tvShow?.title
            releaseFilm.text = tvShow?.date
            popularity.text = tvShow?.episodes
            voteLabel.text = tvShow?.voteAverage
            status.text = tvShow?.status
            overview.text = tvShow?.overview
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

    private fun showAlertDialog() {
        val dialogTitle: String = getString(R.string.dialog_title)
        val dialogMessage: String = getString(R.string.dialog_msg)

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder.setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes)){_, _ ->
                val result = tvHelper.deleteById(tvShow?.id.toString()).toLong()
                if (result > 0){
                    val intent = Intent()
                    intent.putExtra(EXTRA_POSITION, position)
                    setResult(RESULT_DEL_TV, intent)
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
