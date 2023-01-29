package com.example.paging.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.paging.repository.MovieListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val movieListRepository: MovieListRepository):ViewModel() {

    var list = movieListRepository.getMoviesList().cachedIn(viewModelScope)
}