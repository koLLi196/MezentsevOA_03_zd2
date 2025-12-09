package com.example.mezentsevup03_zd2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlin.collections.get

class MovieAdapter(private val onItemClick: (Movie) -> Unit): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var data:List<Movie> = emptyList()
        set(newValue){
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieAdapter.MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        val movie = data[position]

        Picasso.get()
            .load(data[position].Poster)
            .into(holder.moviePoster)
        holder.movieName.text = data[position].Title
        holder.movieMore.text = "Подробнее"

        var genres = "Жанры: "
        for(i in 0..<data[position].Genre.size){
            genres+=data[position].Genre[i]
            if (i+1 <data[position].Genre.size) genres+=", "
        }
        holder.movieGenres.text = genres

        holder.movieMore.setOnClickListener{
            onItemClick(movie)
        }
    }

    override fun getItemCount(): Int = data.size

    class MovieViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val movieName: TextView = itemView.findViewById(R.id.name)
        val moviePoster: ImageView = itemView.findViewById(R.id.poster)
        val movieGenres: TextView = itemView.findViewById(R.id.genres)
        val movieMore:TextView = itemView.findViewById(R.id.more)
    }
}