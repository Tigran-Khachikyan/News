package com.example.news.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.news.domain.models.Article

@Dao
interface NewsDao {

    @Query("SELECT * FROM ARTICLES")
    fun getAll(): LiveData<List<Article>>

    @Query("SELECT * FROM ARTICLES WHERE _id =:id")
    fun getArticle(id: String): LiveData<Article>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(articles: List<Article>)

    @Query("DELETE FROM ARTICLES WHERE isFavourite = 0")
    suspend fun clear()

    @Query("UPDATE ARTICLES SET isFavourite = 0")
    suspend fun removeAllFavourites()

    @Query("SELECT * FROM ARTICLES WHERE isFavourite = 1")
    fun getFavourites(): LiveData<List<Article>>

    @Query("UPDATE ARTICLES SET isFavourite=0 WHERE _id = :id")
    suspend fun removeFavourite(id: String)

    @Query("UPDATE ARTICLES SET isFavourite=1 WHERE _id = :id")
    suspend fun setFavourite(id: String)
}