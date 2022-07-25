package com.szn.lbc.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.szn.lbc.paging.AlbumsPagingDataSource
import com.szn.lbc.repo.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: AlbumsRepository): ViewModel() {

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
        repository.checkDb()
    }

    /*fun getMovieList(): LiveData<PagingData<Album>> {
        return repository.albums
    }*/
}