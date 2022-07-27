package com.szn.lbc.ui.main

import androidx.lifecycle.ViewModel
import com.szn.lbc.repo.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(repository: AlbumsRepository): ViewModel() {


    /**
     * instead of Observable, will collect a Flow
     * for Clean Arch, prefer to let AlbumsRepository handle the logic
     */
    val flow = repository.flow

}