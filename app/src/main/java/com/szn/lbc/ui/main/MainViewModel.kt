package com.szn.lbc.ui.main

import android.graphics.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.szn.lbc.model.Album
import com.szn.lbc.paging.AlbumsPagingDataSource
import com.szn.lbc.repo.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: AlbumsRepository): ViewModel() {

    val albums = repository.albums
    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 20)
    ) {
        AlbumsPagingDataSource(repository)
    }.flow
        .cachedIn(viewModelScope)


    init {
        viewModelScope.launch {
            loadDatas()
        }
    }

    private suspend fun loadDatas() {
        repository.loadAlbums()
    }

    /*fun getMovieList(): LiveData<PagingData<Album>> {
        return repository.albums
    }*/
}