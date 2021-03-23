package com.pascal.udacodingpagging3.view.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.pascal.udacodingpagging3.R
import com.pascal.udacodingpagging3.databinding.FragmentTodoBinding
import com.pascal.udacodingpagging3.databinding.FragmentTodoUserBinding
import com.pascal.udacodingpagging3.model.ResponseListUser
import com.pascal.udacodingpagging3.view.adapter.LoadStateAdapter
import com.pascal.udacodingpagging3.view.adapter.TodoAdapter
import com.pascal.udacodingpagging3.viewModel.ViewModelMain
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TodoUserFragment : Fragment() {

    private var viewModelMain: ViewModelMain? = null
    private var adapter: TodoAdapter? = null
    private var item: ResponseListUser? = null
    private var binding: FragmentTodoUserBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTodoUserBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.actionBar?.title = "Todo Activity"

        initView()
    }

    private fun initView() {
        item = arguments?.getParcelable("data")

        viewModelMain = ViewModelProvider(this).get(ViewModelMain::class.java)
        adapter = TodoAdapter()
        viewModelMain?.setTodoUserId(item?.id!!.toInt())

        setUpAdapter()
        startJob()
    }

    private fun startJob() {
        lifecycleScope.launch {
            viewModelMain?.getTodoUserId()?.collect() {
                adapter?.submitData(it)
            }
        }
    }

    private fun setUpAdapter() {
        binding?.recyclerTodo?.adapter = adapter?.withLoadStateFooter(
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
        viewModelMain?.setTodoUserId(item?.id!!.toInt())
    }
}