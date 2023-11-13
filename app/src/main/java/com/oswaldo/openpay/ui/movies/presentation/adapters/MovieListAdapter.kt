package com.oswaldo.openpay.ui.movies.presentation.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oswaldo.openpay.core.util.extension_functions.loadUrl
import com.oswaldo.openpay.core.util.extension_functions.setOnSafeClickListener
import com.oswaldo.openpay.databinding.ItemMovieThumbBinding
import com.oswaldo.openpay.ui.movies.domain.model.Movie

class MovieListAdapter(private var items: List<Movie>, private var iMovieEvent: IMovieEvent) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMovieThumbBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieThumbBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                binding.thumbMovie.loadUrl(context = holder.itemView.context, url = this.posterPath)
            }
            this.itemView.setOnSafeClickListener {
                iMovieEvent.onClickMovie(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}