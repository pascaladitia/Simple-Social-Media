package com.pascal.udacodingpagging3.view.fragment.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.pascal.udacodingpagging3.view.adapter.CommentAdapter
import com.pascal.udacodingpagging3.view.adapter.LoadStateAdapter
import com.pascal.udacodingpagging3.databinding.FragmentDetailPostBinding
import com.pascal.udacodingpagging3.model.ResponseListPost
import com.pascal.udacodingpagging3.viewModel.ViewModelMain
import kotlinx.android.synthetic.main.fragment_detail_post.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailPostFragment : Fragment() {

    private lateinit var viewModel: ViewModelMain
    private var item: ResponseListPost? = null
    private var adapter: CommentAdapter? = null
    private var binding: FragmentDetailPostBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.actionBar?.title = "Detail Post Activity"
        // Inflate the layout for this fragment
        binding = FragmentDetailPostBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ViewModelMain::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachObserve()
        initView()
    }

    private fun initView() {
        item = arguments?.getParcelable("data")

        viewModel = ViewModelProvider(this).get(ViewModelMain::class.java)
        adapter = CommentAdapter()
        viewModel.detailPostView(item?.id!!.toInt())
        viewModel?.setCommentPost(item?.id!!.toInt())

        setUpAdapter()
        startJob()
    }

    private fun startJob() {
        lifecycleScope.launch {
            viewModel?.getCommentPost()?.collect() {
                adapter?.submitData(it)
            }
        }
    }

    private fun setUpAdapter() {
        binding?.recyclerComment?.adapter = adapter?.withLoadStateFooter(
            footer = LoadStateAdapter() {
                retry()
            }
        )
    }

    private fun retry() {
        adapter?.retry()
    }

    private fun attachObserve() {
        viewModel.detailPost?.observe(viewLifecycleOwner, Observer { showDetailPost(it) })
        viewModel.isError.observe(viewLifecycleOwner, Observer { showError(it) })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { showLoading(it) })
    }

    private fun showDetailPost(it: ResponseListPost) {
        binding?.detailPostTitle?.setText(it?.title)
        binding?.detailPostTime?.setText("post id : ${it?.id!!}")
        binding?.detailPostDesc?.setText(it?.body)
    }

    private fun showError(it: Throwable?) {
        showToast(it.toString())
    }

    private fun showLoading(it: Boolean?) {
        if (it == true) {
            progress_detailPost.visibility = View.VISIBLE
        } else {
            progress_detailPost.visibility = View.GONE
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.detailPostView(item?.id!!.toInt())
        viewModel?.setCommentPost(item?.id!!.toInt())
    }
}