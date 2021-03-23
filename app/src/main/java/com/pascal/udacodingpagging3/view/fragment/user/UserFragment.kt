package com.pascal.udacodingpagging3.view.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.pascal.udacodingpagging3.R
import com.pascal.udacodingpagging3.databinding.FragmentUserBinding
import com.pascal.udacodingpagging3.model.ResponseListUser
import com.pascal.udacodingpagging3.view.adapter.UserAdapter
import com.pascal.udacodingpagging3.viewModel.ViewModelMain

class UserFragment : Fragment() {

    private lateinit var viewModel: ViewModelMain
    private lateinit var navController: NavController
    private var binding: FragmentUserBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater, container, false)
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

        attachObserve()
        viewModel.getUser()
    }

    private fun attachObserve() {
        viewModel.responseUser.observe(viewLifecycleOwner, Observer {showSiswaView(it)})
        viewModel.isError.observe(viewLifecycleOwner, Observer { showError(it) })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { showLoading(it) })
    }

    private fun showSiswaView(it: List<ResponseListUser>) {
        val adapter = UserAdapter(it, object : UserAdapter.OnClickListener {

            override fun detail(item: ResponseListUser?) {
                val bundle = bundleOf("data" to item)
                navController.navigate(R.id.action_userFragment_to_detailUserFragment, bundle)
            }})
        binding?.recyclerUser?.adapter = adapter
    }

    private fun showError(it: Throwable?) {
        showToast(it.toString())
    }

    private fun showLoading(it: Boolean?) {
        if (it == true) {
            binding?.progressUser?.visibility = View.VISIBLE
        } else {
            binding?.progressUser?.visibility = View.GONE
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUser()
    }
}