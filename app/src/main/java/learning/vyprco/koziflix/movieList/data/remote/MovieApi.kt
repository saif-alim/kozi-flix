package learning.vyprco.koziflix.movieList.data.remote

import learning.vyprco.koziflix.movieList.data.remote.response.MovieListDto
import retrofit2.http.*

interface MovieApi {

    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): MovieListDto

    companion object {
        const val BASE_URL = ""
        const val IMAGE_BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = ApiConstants.API_KEY
    }

}