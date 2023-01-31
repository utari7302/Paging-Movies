package com.example.paging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paging.paging.LoaderPagingAdapter
import com.example.paging.paging.MoviePagingAdapter
import com.example.paging.viewmodels.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MoviePagingAdapter
    lateinit var movieListViewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.movieListRecycler)
        adapter = MoviePagingAdapter()

        movieListViewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderPagingAdapter(),
            footer = LoaderPagingAdapter()
        )

        movieListViewModel.list.observe(this, Observer {
            adapter.submitData(lifecycle,it)
        })
    }
}