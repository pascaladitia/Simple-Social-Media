package com.pascal.udacodingpagging3.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.pascal.udacodingpagging3.R
import com.pascal.udacodingpagging3.databinding.ItemPhotoBinding
import com.pascal.udacodingpagging3.model.ResponsePhotoAlbumId


class PhotoAdapter : PagingDataAdapter<ResponsePhotoAlbumId, PhotoAdapter.TodoHolder>(PostDiffUtil()) {

    class PostDiffUtil: DiffUtil.ItemCallback<ResponsePhotoAlbumId>() {
        override fun areItemsTheSame(oldItem: ResponsePhotoAlbumId, newItem: ResponsePhotoAlbumId): Boolean {

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResponsePhotoAlbumId, newItem: ResponsePhotoAlbumId): Boolean {

            return oldItem == newItem
        }

    }

    inner class TodoHolder(var binding: ItemPhotoBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ResponsePhotoAlbumId?) {

            val url = GlideUrl(item?.url, LazyHeaders.Builder()
                    .addHeader("User-Agent", "your-user-agent")
                    .build()
            )

            Glide.with(binding.root)
                .load(url)
                .placeholder(R.drawable.ic_album)
                .into(binding.photoImage);
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoHolder, position: Int) {
        holder.bind(getItem(position))
    }
}