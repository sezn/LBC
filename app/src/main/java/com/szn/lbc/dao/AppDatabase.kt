package com.szn.lbc.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.szn.lbc.model.Album

/**
 * RoomDatabase implementation
 */
@Database(entities = [Album::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun albumDao(): AlbumDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "LBCDb"
        ).build()

    }
}