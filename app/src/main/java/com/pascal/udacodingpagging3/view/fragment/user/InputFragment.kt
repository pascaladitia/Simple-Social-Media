package com.pascal.udacodingpagging3.view.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pascal.udacodingpagging3.databinding.FragmentInputBinding
import com.pascal.udacodingpagging3.model.ResponseListPost
import com.pascal.udacodingpagging3.model.ResponseListUser
import com.pascal.udacodingpagging3.viewModel.ViewModelMain

class InputFragment : Fragment() {

    private lateinit var viewModel: ViewModelMain
    private var binding: FragmentInputBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInputBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ViewModelMain::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        attachObserve()
    }

    private fun initView() {
        var item = arguments?.getParcelable<ResponseListUser>("item")
        var item2 = arguments?.getParcelable<ResponseListPost>("data")

        if (item2 != null) {
            binding?.inputTitle?.setText(item2?.title)
            binding?.inputPost?.setText(item2?.body)

            binding?.btnSave?.text = "Update"
        }

        when (binding?.btnSave?.text) {
            "Update" -> {
                binding!!.btnSave.setOnClickListener {
                    viewModel.updatePostView(
                        item2?.id!!.toInt(),
                        binding?.inputTitle?.text.toString(),
                        binding?.inputPost?.text.toString(),
                        item2?.userId!!.toInt()
                    )
                    activity?.onBackPressed()
                }
            }
            else -> {
                binding!!.btnSave.setOnClickListener {
                    viewModel.addPostView(
                        binding?.inputTitle?.text.toString(),
                        binding?.inputPost?.text.toString(),
                        item?.id!!.toInt()
                    )
                    activity?.onBackPressed()
                }
            }
        }
    }

    private fun attachObserve() {
        viewModel.isEmpty.observe(this, Observer { isEmpty(it) })
        viewModel.isError.observe(this, Observer { showError(it) })
        viewModel.detailPost.observe(this, Observer { showResponse(it) })
    }

    private fun isEmpty(it: Boolean) {
        if (it == true) {
            Toast.makeText(context, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showResponse(it: ResponseListPost?) {
        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    }

    private fun showError(it: Throwable?) {
        Toast.makeText(context, it?.message.toString(), Toast.LENGTH_SHORT).show()
    }
}