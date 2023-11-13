package com.oswaldo.openpay.ui.detail

import android.R
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.oswaldo.openpay.core.util.extension_functions.getSerializable
import com.oswaldo.openpay.core.util.extension_functions.loadUrl
import com.oswaldo.openpay.core.util.extension_functions.setOnSafeClickListener
import com.oswaldo.openpay.databinding.ActivityDetailBinding
import com.oswaldo.openpay.ui.movies.domain.model.Movie


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadExtras()
        setupListeners()
        loadView(movie)
    }

    private fun setupListeners() {
        binding.run {
            btnTrailer.setOnSafeClickListener {
                // here show video player
            }
        }
    }

    private fun loadView(movie: Movie) {
        binding.run {
            posterImage.loadUrl(this@DetailActivity, movie.backdropPath)
            title.text = movie.title
            year.text = movie.releaseDate.substring(0, 4)
            language.text = movie.originalLanguage
            average.text = movie.voteAverage.toString()
            overview.text = movie.overview
        }
    }

    private fun loadExtras() {
        movie = intent.getSerializable(EXTRA_MOVIE, Movie::class.java)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
    }
}