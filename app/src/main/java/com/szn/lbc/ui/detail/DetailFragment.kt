package com.szn.lbc.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.szn.lbc.databinding.FragmentDetailBinding
import com.szn.lbc.model.Album
import com.szn.lbc.network.GlideApp

const val ALBUM_OBJECT = "Album"
/**
 * A simple DetailFragment
 * Display Details about album
 */
class DetailFragment: Fragment() {

    val TAG = DetailFragment::class.java.simpleName
    private lateinit var binding: FragmentDetailBinding
    private lateinit var album: Album

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        album = arguments?.getParcelable(ALBUM_OBJECT)!!
        Log.w(TAG, "onCreate ${album.title}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlideApp.with(this).load(album.url).into(binding.imgIv)
        binding.titleTv.text = album.title
    }

}