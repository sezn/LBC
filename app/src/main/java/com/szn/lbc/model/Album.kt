package com.szn.lbc.model

data class Album (
	val albumId: Int,
	val id: Int,
	val title: String,
	val url: String,
	val thumbnailUrl: String
){
	fun getImage(): String {
		return "$url.jpg"
	}
}