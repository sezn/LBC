package com.szn.lbc.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szn.lbc.model.Album

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<Album>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(album: Album)

    @Query("SELECT * FROM albums")
    fun getAll(): List<Album>

    @Query("SELECT * FROM albums WHERE title LIKE :query")
    fun getByTitle(query: String): List<Album>

    @Query("SELECT * FROM albums")
    fun pagingSource(): PagingSource<Int, Album>

    @Query("DELETE FROM albums")
    suspend fun clearAll()

}