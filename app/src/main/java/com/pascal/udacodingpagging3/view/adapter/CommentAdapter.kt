package com.pascal.udacodingpagging3.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pascal.udacodingpagging3.databinding.ItemCommentBinding
import com.pascal.udacodingpagging3.model.ResponseCommentId

class CommentAdapter : PagingDataAdapter<ResponseCommentId, CommentAdapter.CommentHolder>(PostDiffUtil()) {

    class PostDiffUtil : DiffUtil.ItemCallback<ResponseCommentId>() {
        override fun areItemsTheSame(
            oldItem: ResponseCommentId,
            newItem: ResponseCommentId
        ): Boolean {

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ResponseCommentId,
            newItem: ResponseCommentId
        ): Boolean {

            return oldItem == newItem
        }

    }

    inner class CommentHolder(var binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseCommentId?) {
            binding.commentTitle.text = item?.name
            binding.commentDesc.text = item?.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        holder.bind(getItem(position))
    }
}