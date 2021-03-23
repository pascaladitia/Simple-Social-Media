package com.pascal.udacodingpagging3.view.fragment.post

import android.os.Bundle
import android.util.Log
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
import com.pascal.udacodingpagging3.view.adapter.LoadStateAdapter
import com.pascal.udacodingpagging3.view.adapter.PostAdapter
import com.pascal.udacodingpagging3.databinding.FragmentPostBinding
import com.pascal.udacodingpagging3.model.ResponseListPost
import com.pascal.udacodingpagging3.viewModel.ViewModelMain
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PostFragment : Fragment() {

    private var viewModelMain: ViewModelMain? = null
    private lateinit var navController: NavController
    private var adapter: PostAdapter? = null
    private var binding: FragmentPostBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.actionBar?.title = "Post Activity"
        // Inflate the layout for this fragment
        binding = FragmentPostBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        initView()
    }

    private fun initView() {
        viewModelMain = ViewModelProvider(this).get(ViewModelMain::class.java)
        adapter = context?.let {
            PostAdapter(it) {
                toDetail(it)
            }
        }
        viewModelMain?.setPost()

        setUpAdapter()
        startJob()
    }

    private fun toDetail(it: ResponseListPost) {
        Log.d("itemData", it.id.toString())
        val bundle = bundleOf("data" to it)
        navController.navigate(R.id.action_postFragment_to_detailPostFragment, bundle)
    }

    private fun startJob() {
        lifecycleScope.launch {
            viewModelMain?.getPost()?.collect() {
                adapter?.submitData(it)
            }
        }
    }

    private fun setUpAdapter() {
        binding?.recyclerPost?.adapter = adapter?.withLoadStateFooter(
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
        viewModelMain?.setPost()
    }
}