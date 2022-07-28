package com.dilara.retrofirkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dilara.retrofirkotlin.models.Movie
import com.dilara.retrofirkotlin.models.MovieResponse
import com.dilara.retrofirkotlin.services.MovieApiInterface
import com.dilara.retrofirkotlin.services.MovieApiService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_movies_list.layoutManager=LinearLayoutManager(this)
        rv_movies_list.setHasFixedSize(true)
        getMovieData { movies:List<Movie> -> rv_movies_list.adapter=MovieAdapter(movies)
        }
    }
    private fun getMovieData(callback:(List<Movie>)->Unit){
        val apiService=MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getMovieList().enqueue(object:retrofit2.Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                return callback(response.body()!!.movies)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }

        })


    }
}