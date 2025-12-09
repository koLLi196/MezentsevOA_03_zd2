package com.example.mezentsevup03_zd2

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : Activity() {
    val apiKey = "900abc80"
    val url = "https://www.omdbapi.com/?s=movie&apikey=$apiKey"

    lateinit var genresSpinner:Spinner
    lateinit var searchText:EditText
    val movies = ArrayList<Movie>()
    val adapterList = MovieAdapter{ movie->
        val fragment = InfoMovieFragment().apply {
            arguments = Bundle().apply {
                putString("title", movie.Title)
                putString("poster", movie.Poster)
                putString("plot", movie.Plot)
                putString("year", movie.Year)
                putString("runtime", movie.Runtime)
                putStringArray("genres", movie.Genre.toTypedArray())
            }
        }
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchText = findViewById(R.id.film_name)
        genresSpinner = findViewById(R.id.genres_spinner)
        val allGenres = ArrayList<String>()
        allGenres.add("Нет")

        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                    response ->
                val obj = JSONObject(response)
                val tempArray = obj.getJSONArray("Search")

                for(i in 0..<tempArray.length()){
                    val temp = tempArray.getJSONObject(i)

                    val urlMovie = "https://www.omdbapi.com/?apikey=${apiKey}&i=${temp.getString("imdbID")}"
                    val queueMovie = Volley.newRequestQueue(this)
                    val stringRequest = StringRequest(
                        Request.Method.GET,
                        urlMovie,
                        {
                                responseMovie ->
                            val movieObj = JSONObject(responseMovie)
                            val moviesGenres = movieObj.getString("Genre").split(", ")

                            movies.add(Movie(
                                movieObj.getString("Title"),
                                movieObj.getString("Poster"),
                                moviesGenres,
                                movieObj.getString("Plot"),
                                movieObj.getString("Year"),
                                movieObj.getString("Runtime")
                            ))

                            for(j in 0..<moviesGenres.size){
                                if(!allGenres.contains(moviesGenres[j].trim(' '))){
                                    allGenres.add(moviesGenres[j].trim(' '))
                                }
                            }

                            adapterList.data = movies
                        },
                        {
                            Log.d("MyLog", "VOLLEY ERROR: $it")
                        }
                    )
                    queueMovie.add(stringRequest)
                }
            },
            {
                Log.d("MyLog", "VOLLEY ERROR: $it")
            }
        )
        queue.add(stringRequest)

        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, allGenres)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genresSpinner.adapter = adapterSpinner

        genresSpinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position)

                if(selectedItem!="Нет"){
                    val tempMovies = ArrayList<Movie>()
                    for(i in 0..<movies.size){
                        if (movies[i].Genre.contains(selectedItem)) tempMovies.add(movies[i])
                    }
                    adapterList.data = tempMovies
                }
                else adapterList.data = movies
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        val listFilms:RecyclerView = findViewById(R.id.list_films)

        listFilms.layoutManager = GridLayoutManager(this, 3)
        listFilms.adapter = adapterList
    }

    fun Search(view: View) {
        val tempMovies = ArrayList<Movie>()
        if(searchText.text.trim(' ').isNotEmpty()){
            for(i in 0..<movies.size){
                if (movies[i].Title.lowercase() == searchText.text.toString().trim(' ').lowercase()) tempMovies.add(movies[i])
            }
            adapterList.data = tempMovies
        }
        else{
            adapterList.data = movies
        }
    }
}