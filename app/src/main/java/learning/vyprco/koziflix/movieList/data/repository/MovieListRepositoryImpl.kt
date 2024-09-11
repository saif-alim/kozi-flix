package learning.vyprco.koziflix.movieList.data.repository

import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import learning.vyprco.koziflix.movieList.data.local.movie.MovieDatabase
import learning.vyprco.koziflix.movieList.data.mapper.toMovie
import learning.vyprco.koziflix.movieList.data.mapper.toMovieEntity
import learning.vyprco.koziflix.movieList.data.remote.MovieApi
import learning.vyprco.koziflix.movieList.domain.model.Movie
import learning.vyprco.koziflix.movieList.domain.repository.MovieListRepository
import learning.vyprco.koziflix.movieList.util.Resource
import retrofit2.HttpException
import java.io.IOException

class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {
    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))
            val localMovieList = movieDatabase.movieDao.getMovieListByCategory(category)

            // Load locally
            if (localMovieList.isNotEmpty() && !forceFetchFromRemote) {
                emit(
                    Resource.Success(
                        data = localMovieList.map {
                            movieEntity -> movieEntity.toMovie(category)
                        }
                    ))
                emit(Resource.Loading(false))
                return@flow
            }

            val movieListFromApi = try {
                movieApi.getMoviesList(category, page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }

            val movieEntities = movieListFromApi.results.let {
                it.map { movieDto ->
                    movieDto.toMovieEntity(category)
                }
            }

            movieDatabase.movieDao.upsertMovieList(movieEntities)

            emit(Resource.Success(
                movieEntities.map { it.toMovie(category) }
            ))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        TODO("Not yet implemented")
    }
}