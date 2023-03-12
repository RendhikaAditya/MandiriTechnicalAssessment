package co.id.tesmandiritmdb.view.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.id.tesmandiritmdb.network.Repository
import co.id.tesmandiritmdb.network.Resource
import co.id.tesmandiritmdb.network.response.GenreResponse
import co.id.tesmandiritmdb.network.response.MovieResponse
import kotlinx.coroutines.launch

class MainViewModel(
    val repository: Repository
) : ViewModel() {

    val movieTranding: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
    val genre:MutableLiveData<Resource<GenreResponse>> = MutableLiveData()
    val movie:MutableLiveData<Resource<MovieResponse>> = MutableLiveData()


    fun fetchMovieTrand() = viewModelScope.launch {
        movieTranding.value = Resource.Loading()
        try {
            val response = repository.fetchMovieTrand()
            movieTranding.value = Resource.Success(response.body()!!)
        } catch (e: Exception) {
            movieTranding.value = Resource.Error(e.message.toString())
        }
    }

    fun fetchMovie(gendre : String, page : String)= viewModelScope.launch {
        movie.value = Resource.Loading()
        try {
            val response = repository.fetchMovie(gendre, page)
            movie.value = Resource.Success(response.body()!!)
        }catch (e:Exception){
            movie.value = Resource.Error(e.message.toString())
        }
    }


    fun fetchGenre() = viewModelScope.launch {
        genre.value = Resource.Loading()
        try {
            val response = repository.fetchGenre()
            genre.value = Resource.Success(response.body()!!)
        } catch (e: Exception) {
            genre.value = Resource.Error(e.message.toString())
        }
    }

}