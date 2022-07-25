package com.szn.lbc.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class Album (
	@PrimaryKey
	val id: Int,
	val albumId: Int,
	val title: String,
	val url: String,
	val thumbnailUrl: String
){
	fun getImage(): String {
		return "$url.jpg"
	}
}