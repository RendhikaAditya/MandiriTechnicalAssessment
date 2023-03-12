package co.id.tesmandiritmdb.network

class Repository(
    private val api: EndPoin
) {
    suspend fun fetchMovieTrand() = api.trandingMovie()
    suspend fun fetchGenre() = api.genreList()
    suspend fun fetchMovie(gendre :String, page :String) = api.movieList(gendre, page)
    suspend fun fetchDetailMovie(id:String) = api.movieDetail(id)
    suspend fun fetchTrailer(id:String) = api.trailer(id)
    suspend fun fetchUlasan(id:String, page :String) = api.ulasan(id, page)
}