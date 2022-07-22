package com.szn.lbc.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.szn.lbc.model.Album
import com.szn.lbc.repo.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: AlbumsRepository): ViewModel() {

    val albums = repository.albums

    init {
        viewModelScope.launch {
            loadDatas()
        }
    }

    private suspend fun loadDatas() {
        repository.loadAlbums()
    }
}