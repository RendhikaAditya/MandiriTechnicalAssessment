package co.id.tesmandiritmdb.network

import co.id.tesmandiritmdb.network.response.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EndPoin {

    @GET("trending/movie/week")
    suspend fun trandingMovie(): Response<MovieResponse>

    @GET("genre/movie/list")
    suspend fun genreList(): Response<GenreResponse>


    @GET("discover/movie")
    suspend fun movieList(
       @Query("with_genres") gendre: String,
       @Query("page") page: String
    ): Response<MovieResponse>

    @GET("movie/{id}")
    suspend fun movieDetail(
        @Path("id") id: String
    ): Response<DetailMovieResponse>

    @GET("movie/{id}/videos")
    suspend fun trailer(
        @Path("id") id: String
    ):Response<TrailerResponse>

    @GET("movie/{id}/reviews")
    suspend fun ulasan(
        @Path("id") id: String,
        @Query("page") page: String
    ):Response<UlasanResponse>

}