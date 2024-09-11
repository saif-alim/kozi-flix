package learning.vyprco.koziflix.movieList.domain.repository

import kotlinx.coroutines.flow.Flow
import learning.vyprco.koziflix.movieList.domain.model.Movie
import learning.vyprco.koziflix.movieList.util.Resource

interface MovieListRepository {

    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int,
    ): Flow<Resource<List<Movie>>>


    suspend fun getMovie(id: Int): Flow<Resource<Movie>>
}