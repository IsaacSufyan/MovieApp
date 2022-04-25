package com.isaacsufyan.androidarchitecture.mvvm.movieapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.R
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.model.Movie
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.net.RetrofitClient
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.databinding.ItemMovieMainBinding
import java.util.*

class MovieListAdapter(private val movies: MutableList<Movie>) :
    RecyclerView.Adapter<MovieListAdapter.MovieHolder>() {

    val selectedMovies = HashSet<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding =
            ItemMovieMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun setMovies(movieList: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movieList)
        notifyDataSetChanged()
    }

    inner class MovieHolder(private val binding: ItemMovieMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) = with(binding.root) {
            binding.movieTitleTextView.text = movie.title
            binding.movieReleaseDateTextView.text = movie.releaseDate
            binding.checkbox.isChecked = movie.watched
            if (movie.posterPath != null)
                Glide.with(this).load(RetrofitClient.IMAGE_URL + movie.posterPath)
                    .into(binding.movieImageView)
            else {
                binding.movieImageView.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_local_movies_gray,
                        null
                    )
                )
            }
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (!selectedMovies.contains(movie) && isChecked) {
                    selectedMovies.add(movie)
                } else {
                    selectedMovies.remove(movie)
                }
            }
        }
    }
}