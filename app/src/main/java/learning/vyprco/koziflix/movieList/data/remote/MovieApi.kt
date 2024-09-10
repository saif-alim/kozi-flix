package learning.vyprco.koziflix.movieList.data.remote

import retrofit2.http.GET

interface MovieApi {

    @GET()
    suspend fun getMoviesList()

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = ""
    }

}