package com.pascal.udacodingpagging3.view.fragment.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.pascal.udacodingpagging3.view.adapter.LoadStateAdapter
import com.pascal.udacodingpagging3.view.adapter.TodoAdapter
import com.pascal.udacodingpagging3.databinding.FragmentTodoBinding
import com.pascal.udacodingpagging3.viewModel.ViewModelMain
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TodoFragment : Fragment() {

    private var viewModelMain: ViewModelMain? = null
    private var adapter: TodoAdapter? = null
    private var binding: FragmentTodoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { binding = FragmentTodoBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.actionBar?.title = "Post Activity"

        initView()
    }

    private fun initView() {
        viewModelMain = ViewModelProvider(this).get(ViewModelMain::class.java)
        adapter = TodoAdapter()
        viewModelMain?.setTodo()

        setUpAdapter()
        startJob()
    }

    private fun startJob() {
        lifecycleScope.launch {
            viewModelMain?.getTodo()?.collect() {
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
        viewModelMain?.setTodo()
    }
}