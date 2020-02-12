package com.example.appmovies.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.appmovies.app.MyApp;
import com.example.appmovies.data.local.MovieRoomDataBase;
import com.example.appmovies.data.local.dao.MovieDao;
import com.example.appmovies.data.local.entity.MovieEntity;
import com.example.appmovies.data.netWork.NetworkBoundResource;
import com.example.appmovies.data.netWork.Resource;
import com.example.appmovies.data.remote.ApiConstants;
import com.example.appmovies.data.remote.MovieApiService;
import com.example.appmovies.data.remote.RequestInterceptor;
import com.example.appmovies.data.remote.model.MoviesResponse;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {
    private final MovieApiService movieApiService;
    private final MovieDao movieDao;

    public MovieRepository() {
        //Datos locales
        MovieRoomDataBase movieRoomDataBase = Room.databaseBuilder(
                MyApp.getContext(),
                MovieRoomDataBase.class,
                "db_movies2"
        ).build();
        movieDao = movieRoomDataBase.getMovieDao();

        //Request interceptor incluir la cabecera (URL) de la peticion el token oAPI_KEY al usuario
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new RequestInterceptor());
        OkHttpClient client = okHttpClientBuilder.build();

        //Remote retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        movieApiService = retrofit.create(MovieApiService.class);
    }

    public LiveData<Resource<List<MovieEntity>>> getPopularMovies() {
        //Tipo que devuelve room
        //segundo tipo que devuelve la api de retrofit
        return new NetworkBoundResource<List<MovieEntity>, MoviesResponse>() {

            //Guarda la respues del servidor en la base de datos local por si no hay internet optamos la inofmracion de aqui
            @Override
            protected void saveCallResult(@NonNull MoviesResponse item) {
                movieDao.saveMovies(item.getResults());
            }

            //Devuelve los datos que dispongamos en room bd local
            @NonNull
            @Override
            protected LiveData<List<MovieEntity>> loadFromDb() {
                return movieDao.loadMovies();
            }

            //Realiza la llamada a la APIA EXTERNA, obtenemos los datos de la api remote
            @NonNull
            @Override
            protected Call<MoviesResponse> createCall() {
                return movieApiService.loadPopularMovies();
            }
        }.getAsLiveData();
    }

}
