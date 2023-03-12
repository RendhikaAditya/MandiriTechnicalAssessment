package co.id.tesmandiritmdb.view.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.id.tesmandiritmdb.network.Repository
import co.id.tesmandiritmdb.network.Resource
import co.id.tesmandiritmdb.network.response.*
import kotlinx.coroutines.launch

class DetailViewModel(
    val repository: Repository
) : ViewModel() {


    val movieDetail:MutableLiveData<Resource<DetailMovieResponse>> = MutableLiveData()
    val trailer: MutableLiveData<Resource<TrailerResponse>> = MutableLiveData()
    val ulasan : MutableLiveData<Resource<UlasanResponse>> = MutableLiveData()

    fun fetchMovieDetail(id:String) = viewModelScope.launch {
        movieDetail.value = Resource.Loading()
        try {
            val response = repository.fetchDetailMovie(id)
            movieDetail.value = Resource.Success(response.body()!!)
        } catch (e: Exception) {
            movieDetail.value = Resource.Error(e.message.toString())
        }
    }

    fun fetchTrailer(id:String) = viewModelScope.launch {
        trailer.value = Resource.Loading()
        try {
            val response = repository.fetchTrailer(id)
            trailer.value = Resource.Success(response.body()!!)
        } catch (e: Exception) {
            trailer.value = Resource.Error(e.message.toString())
        }
    }

    fun fetchUlasan(id:String, page :String) = viewModelScope.launch {
        ulasan.value = Resource.Loading()
        try {
            val response = repository.fetchUlasan(id, page)
            ulasan.value = Resource.Success(response.body()!!)
        } catch (e: Exception) {
            ulasan.value = Resource.Error(e.message.toString())
        }
    }


}