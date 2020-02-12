package com.example.appmovies.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.appmovies.data.MovieRepository;
import com.example.appmovies.data.local.entity.MovieEntity;
import com.example.appmovies.data.netWork.Resource;

import java.util.List;

public class MovieViewModel extends ViewModel {

    private final LiveData<Resource<List<MovieEntity>>> popularMovies;
    private MovieRepository movieRepository;

    public MovieViewModel() {
        movieRepository = new MovieRepository();
        popularMovies = movieRepository.getPopularMovies();
    }

    //delvuelve el listada
    public LiveData<Resource<List<MovieEntity>>> getPopularMovies(){
        return popularMovies;
    }
}
