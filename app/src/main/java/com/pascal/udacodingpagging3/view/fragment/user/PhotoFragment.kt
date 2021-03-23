package com.pascal.udacodingpagging3.view.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.pascal.udacodingpagging3.R
import com.pascal.udacodingpagging3.databinding.FragmentPhotoBinding
import com.pascal.udacodingpagging3.databinding.FragmentTodoUserBinding
import com.pascal.udacodingpagging3.model.ResponseListAlbumUserId
import com.pascal.udacodingpagging3.model.ResponseListUser
import com.pascal.udacodingpagging3.model.ResponsePhotoAlbumId
import com.pascal.udacodingpagging3.view.adapter.LoadStateAdapter
import com.pascal.udacodingpagging3.view.adapter.PhotoAdapter
import com.pascal.udacodingpagging3.view.adapter.TodoAdapter
import com.pascal.udacodingpagging3.viewModel.ViewModelMain
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PhotoFragment : Fragment() {

    private var viewModelMain: ViewModelMain? = null
    private var adapter: PhotoAdapter? = null
    private var item: ResponseListAlbumUserId? = null
    private var binding: FragmentPhotoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.actionBar?.title = "Photo Activity"

        initView()
    }

    private fun initView() {
        item = arguments?.getParcelable("data")

        viewModelMain = ViewModelProvider(this).get(ViewModelMain::class.java)
        adapter = PhotoAdapter()
        viewModelMain?.setPhotoAlbumId(item?.id!!.toInt())

        setUpAdapter()
        startJob()
    }

    private fun startJob() {
        lifecycleScope.launch {
            viewModelMain?.getPhotoAlbumId()?.collect() {
                adapter?.submitData(it)
            }
        }
    }

    private fun setUpAdapter() {
        binding?.recyclerPhoto?.adapter = adapter?.withLoadStateFooter(
            footer = LoadStateAdapter() {
                retry()
            }
        )
    }

    private fun retry() {
        adapter?.retry()
    }

    override fun onResume() {
        super.onResume()
        viewModelMain?.setPhotoAlbumId(item?.id!!.toInt())
    }
}