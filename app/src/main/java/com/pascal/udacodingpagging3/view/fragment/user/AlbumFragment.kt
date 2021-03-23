package com.pascal.udacodingpagging3.view.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.pascal.udacodingpagging3.R
import com.pascal.udacodingpagging3.databinding.FragmentAlbumBinding
import com.pascal.udacodingpagging3.model.ResponseListAlbumUserId
import com.pascal.udacodingpagging3.model.ResponseListUser
import com.pascal.udacodingpagging3.view.adapter.AlbumAdapter
import com.pascal.udacodingpagging3.view.adapter.LoadStateAdapter
import com.pascal.udacodingpagging3.viewModel.ViewModelMain
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AlbumFragment : Fragment() {

    private var viewModelMain: ViewModelMain? = null
    private lateinit var navController: NavController
    private var item: ResponseListUser? = null
    private var adapter: AlbumAdapter? = null
    private var binding: FragmentAlbumBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAlbumBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.actionBar?.title = "Post Activity"

        navController = Navigation.findNavController(view)
        initView()
    }

    private fun initView() {
        item = arguments?.getParcelable("data")

        viewModelMain = ViewModelProvider(this).get(ViewModelMain::class.java)
        adapter = context?.let {
            AlbumAdapter(it) {
                toDetail(it)
            }
        }
        viewModelMain?.setAlbumUserId(item?.id!!.toInt())

        setUpAdapter()
        startJob()
    }

    private fun toDetail(it: ResponseListAlbumUserId) {
        val bundle = bundleOf("data" to it)
        navController.navigate(R.id.action_albumFragment_to_photoFragment, bundle)
    }

    private fun startJob() {
        lifecycleScope.launch {
            viewModelMain?.getAlbumUserId()?.collect() {
                adapter?.submitData(it)
            }
        }
    }

    private fun setUpAdapter() {
        binding?.recyclerAlbum?.adapter = adapter?.withLoadStateFooter(
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
        viewModelMain?.setAlbumUserId(item?.id!!.toInt())
    }
}