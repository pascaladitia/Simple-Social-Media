package com.pascal.udacodingpagging3.view.fragment.user

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.pascal.udacodingpagging3.R
import com.pascal.udacodingpagging3.databinding.FragmentDetailUserBinding
import com.pascal.udacodingpagging3.model.ResponseListPost
import com.pascal.udacodingpagging3.model.ResponseListUser
import com.pascal.udacodingpagging3.view.adapter.LoadStateAdapter
import com.pascal.udacodingpagging3.view.adapter.PostUserAdapter
import com.pascal.udacodingpagging3.viewModel.ViewModelMain
import kotlinx.android.synthetic.main.dialog_post.view.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailUserFragment : Fragment() {

    private lateinit var viewModel: ViewModelMain
    private lateinit var navController: NavController
    private var item: ResponseListUser? = null
    private var adapter: PostUserAdapter? = null
    private var binding: FragmentDetailUserBinding? = null
    private var dialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ViewModelMain::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        initView()
    }

    private fun initView() {
        item = arguments?.getParcelable("data")

        binding?.detailUserName?.setText(item?.name)
        binding?.detailUserEmail?.setText(item?.email)
        binding?.detailUserPhone?.setText(item?.phone)
        binding?.detailUserUsername?.setText(item?.username)
        binding?.detailUserTxt?.setText("Post ${item?.name}")

        binding?.detailUserAlbum?.setOnClickListener {
            val bundle = bundleOf("data" to item)
            navController.navigate(R.id.action_detailUserFragment_to_albumFragment, bundle)
        }

        binding?.detailUserTodo?.setOnClickListener {
            val bundle = bundleOf("data" to item)
            navController.navigate(R.id.action_detailUserFragment_to_todoUserFragment, bundle)
        }

        binding?.detailUserAddPost?.setOnClickListener {
            val bundle = bundleOf("item" to item)
            navController.navigate(R.id.action_detailUserFragment_to_inputFragment, bundle)
        }

        viewModel = ViewModelProvider(this).get(ViewModelMain::class.java)

        adapter = context?.let {
            PostUserAdapter(it) {
                toDetail(it)
            }
        }

        viewModel?.setPostUserId(item?.id!!.toInt())

        setUpAdapter()
        startJob()
    }

    private fun toDetail(it: ResponseListPost) {
        val bundle = bundleOf("data" to it)
        //navController.navigate(R.id.action_detailUserFragment_to_detailPostFragment, bundle)

        val window = context?.let { it1 -> AlertDialog.Builder(it1) }
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_post, null)
        window?.setView(view)

        view.dialog_update.setOnClickListener {
            navController.navigate(R.id.action_detailUserFragment_to_inputFragment, bundle)
            dialog?.dismiss()
        }

        view.dialog_delete.setOnClickListener {
            dialog?.dismiss()
        }

        dialog = window?.create()
        dialog?.show()
    }

    private fun startJob() {
        lifecycleScope.launch {
            viewModel?.getPostUserId()?.collect() {
                adapter?.submitData(it)
            }
        }
    }

    private fun setUpAdapter() {
        binding?.recyclerDetailpost?.adapter = adapter?.withLoadStateFooter(
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
        viewModel?.setPost()
    }
}