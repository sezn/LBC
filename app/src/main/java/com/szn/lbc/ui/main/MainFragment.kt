package com.szn.lbc.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.szn.lbc.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment: Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentMainBinding
    private lateinit var mAdapter: AlbumsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = AlbumsAdapter()
        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.adapter = mAdapter

        lifecycleScope.async{
            viewModel.flow.collect {
                mAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            mAdapter.loadStateFlow.collectLatest { loadStates ->
            Log.e(TAG, "stateFlow collect $loadStates")
                binding.progress.isVisible = loadStates.refresh is LoadState.Loading
             }
        }

    }


    companion object {
        fun newInstance() = MainFragment()
        val TAG = MainFragment::class.java.simpleName
    }
}