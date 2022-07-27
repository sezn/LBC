package com.szn.lbc.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.szn.lbc.R
import com.szn.lbc.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Fragment responsible for Display the RecyclerView
 */
@AndroidEntryPoint
class MainFragment: Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentMainBinding
    private val mAdapter = AlbumsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.adapter = mAdapter
        binding.progress.isVisible = true
        val divider = DividerItemDecoration(context, RecyclerView.VERTICAL)
        val shape = ResourcesCompat.getDrawable(context!!.resources, R.drawable.divider_shape, null)
        divider.setDrawable(shape!!)
        binding.recycler.addItemDecoration(divider)

        lifecycleScope.launchWhenCreated {
            launch {
                mAdapter.loadStateFlow.collectLatest { loadStates ->
                    binding.progress.isVisible = loadStates.refresh is LoadState.Loading
                }
            }

            launch {
                viewModel.flow.collect {
                    mAdapter.submitData(it)
                }
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
        val TAG = MainFragment::class.java.simpleName
    }
}