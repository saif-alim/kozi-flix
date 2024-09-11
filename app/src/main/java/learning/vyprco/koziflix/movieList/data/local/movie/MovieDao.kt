package learning.vyprco.koziflix.movieList.data.local.movie

import androidx.room.*
import java.util.Locale.Category

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertMovieList(movieList: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity

    @Query("SELECT * FROM MovieEntity WHERE category = :category")
    suspend fun getMovieListByCategory(category: String): List<MovieEntity>
}