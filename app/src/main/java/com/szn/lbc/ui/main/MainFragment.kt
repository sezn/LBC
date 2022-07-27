package com.szn.lbc.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.szn.lbc.R
import com.szn.lbc.databinding.FragmentMainBinding
import com.szn.lbc.model.Album
import com.szn.lbc.ui.detail.ALBUM_OBJECT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Fragment responsible for Display the RecyclerView
 */
@AndroidEntryPoint
class MainFragment: Fragment(), OnItemSelectedListener {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentMainBinding
    private val mAdapter = AlbumsAdapter(this)

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

    /**
     * on Item clicked, send to Detail View
     */
    override fun onClick(position: Int, album: Album) {
        Log.w(TAG, "onClick $position ${album.title}")
        val bdl = Bundle().apply {
            this.putParcelable(ALBUM_OBJECT, album)
        }
        findNavController().navigate(R.id.action_go_to_detail, bdl)
    }

    companion object {
        val TAG = MainFragment::class.java.simpleName
    }
}