package com.szn.lbc.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.szn.lbc.model.Album

@OptIn(ExperimentalPagingApi::class)
class AlbumRemoteMediator(): RemoteMediator<Int, Album>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Album>): MediatorResult {
        TODO("Not yet implemented")
    }
}