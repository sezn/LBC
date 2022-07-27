package com.szn.lbc.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class for represent the Album
 * And the Entity for DAO
 */
@Entity(tableName = "albums")
data class Album (
	@PrimaryKey
	val id: Int,
	val albumId: Int,
	val title: String,
	val url: String,
	val thumbnailUrl: String
)