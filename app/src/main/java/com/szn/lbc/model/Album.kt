package com.szn.lbc.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Data class for represent the Album
 * And the Entity for DAO
 */
@Entity(tableName = "albums")
@Parcelize
data class Album (
	@PrimaryKey
	val id: Int,
	val albumId: Int,
	val title: String,
	val url: String,
	val thumbnailUrl: String
) : Parcelable