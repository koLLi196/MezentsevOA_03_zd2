package com.example.mezentsevup03_zd2

import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class InfoMovieFragment : Fragment() {

    lateinit var movie:Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = arguments.getString("title").toString()
        val poster = arguments.getString("poster").toString()
        val genre = arguments.getStringArray("genres")!!.toList()
        val plot = arguments.getString("plot").toString()
        val year = arguments.getString("year").toString()
        val runtime = arguments.getString("runtime").toString()

        movie = Movie(
            title,
            poster,
            genre,
            plot,
            year,
            runtime
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_info_movie, container, false)

        val name:TextView = view.findViewById(R.id.film_name)
        val poster:ImageView = view.findViewById(R.id.poster)
        val genre:TextView = view.findViewById(R.id.genres_film)
        val year:TextView = view.findViewById(R.id.year_film)
        val runtime:TextView = view.findViewById(R.id.runtime_film)
        val plot:TextView = view.findViewById(R.id.plot_film)

        name.text = movie.Title
        Picasso.get()
            .load(movie.Poster)
            .into(poster)
        genre.text="Жанры: " + movie.Genre.joinToString(", ")
        year.text = "Дата выпуска: ${movie.Year}"
        runtime.text = "Длительность: ${movie.Runtime}"
        plot.text = "Описание: ${movie.Plot}"
        return view
    }

    override fun onStart() {
        super.onStart()
    }
}