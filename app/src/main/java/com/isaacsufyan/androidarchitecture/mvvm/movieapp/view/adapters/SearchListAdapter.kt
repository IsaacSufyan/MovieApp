package com.isaacsufyan.androidarchitecture.mvvm.movieapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.R
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.model.Movie
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.net.RetrofitClient
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.databinding.ItemMovieSearchBinding

class SearchListAdapter(
    private val movies: MutableList<Movie>,
    private var listener: (Movie) -> Unit
) : RecyclerView.Adapter<SearchListAdapter.MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding =
            ItemMovieSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(movies[position], position)
    }

    fun setMovies(movieList: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movieList)
        notifyDataSetChanged()
    }

    inner class MovieHolder(private val binding: ItemMovieSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, position: Int) = with(binding.root) {
            binding.searchTitleTextView.text = movie.title
            binding.searchReleaseDateTextView.text = movie.releaseDate
            binding.root.setOnClickListener { listener(movies[position]) }
            if (movie.posterPath != null)
                Glide.with(this).load(RetrofitClient.IMAGE_URL + movie.posterPath)
                    .into(binding.searchImageView)
            else {
                binding.searchImageView.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_local_movies_gray,
                        null
                    )
                )
            }
        }
    }
}