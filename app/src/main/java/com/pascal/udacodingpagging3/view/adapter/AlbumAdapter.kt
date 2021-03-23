package com.pascal.udacodingpagging3.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pascal.udacodingpagging3.databinding.ItemAlbumBinding
import com.pascal.udacodingpagging3.databinding.ItemTodoBinding
import com.pascal.udacodingpagging3.model.ResponseListAlbumUserId
import com.pascal.udacodingpagging3.model.ResponseListPost

class AlbumAdapter(val context: Context, private val list: (ResponseListAlbumUserId) -> Unit)
    : PagingDataAdapter<ResponseListAlbumUserId, AlbumAdapter.TodoHolder>(PostDiffUtil()) {

    class PostDiffUtil: DiffUtil.ItemCallback<ResponseListAlbumUserId>() {
        override fun areItemsTheSame(oldItem: ResponseListAlbumUserId, newItem: ResponseListAlbumUserId): Boolean {

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResponseListAlbumUserId, newItem: ResponseListAlbumUserId): Boolean {

            return oldItem == newItem
        }

    }

    inner class TodoHolder(var binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ResponseListAlbumUserId?) {
            binding.albumTitle.text = item?.title
            binding.albumId.text = "AlbumId : ${item?.id.toString()}"

            binding.root.setOnClickListener {
                if (item != null) {
                    list(item)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoHolder, position: Int) {
        holder.bind(getItem(position))
    }
}