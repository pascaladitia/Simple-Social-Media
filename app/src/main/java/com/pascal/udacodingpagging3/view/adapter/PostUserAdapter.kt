package com.pascal.udacodingpagging3.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pascal.udacodingpagging3.databinding.ItemPostUserBinding
import com.pascal.udacodingpagging3.model.ResponseListPost

class PostUserAdapter(
    val context: Context,
    private val list: (ResponseListPost) -> Unit) :
    PagingDataAdapter<ResponseListPost, PostUserAdapter.PostHolder>(PostDiffUtil()) {

    class PostDiffUtil : DiffUtil.ItemCallback<ResponseListPost>() {
        override fun areItemsTheSame(
            oldItem: ResponseListPost,
            newItem: ResponseListPost
        ): Boolean {

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ResponseListPost,
            newItem: ResponseListPost
        ): Boolean {

            return oldItem == newItem
        }

    }

    inner class PostHolder(var binding: ItemPostUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseListPost?) {
            binding.postTitle.text = item?.title
            binding.postDesc.text = item?.body
            binding.postTime.text = "id post : ${item?.id.toString()}"

            binding.rootView.setOnClickListener {
                if (item != null) {
                    list(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = ItemPostUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.bind(getItem(position))
    }
}