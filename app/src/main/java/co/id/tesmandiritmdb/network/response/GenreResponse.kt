package co.id.tesmandiritmdb.network.response

data class GenreResponse(
    val genres: List<Genre>
){
    data class Genre(
        val id: Int,
        val name: String
    )
}

