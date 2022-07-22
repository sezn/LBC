package com.szn.lbc.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.szn.lbc.model.Album
import com.szn.lbc.network.APIService
import com.szn.lbc.repo.AlbumsRepository

/**
 * PagingSource implementation of [Album]
 */
class AlbumsPagingDataSource(private val repository: AlbumsRepository): PagingSource<Int, Album>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Album> {
        return try {

            val currentLoadingPageKey = params.key ?: 1
            val response = repository.albums

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Album>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)}
    }



}