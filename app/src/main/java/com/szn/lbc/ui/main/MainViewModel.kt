package com.szn.lbc.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szn.lbc.repo.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: AlbumsRepository): ViewModel() {

    val flow = repository.flow

    init {
//        No need at now as it s handled by AlbumRemoteMediator
//        Just let it to show old logic
       viewModelScope.launch {
            loadDatas()
        }
    }

    private suspend fun loadDatas() {
        repository.checkDb()
    }

}