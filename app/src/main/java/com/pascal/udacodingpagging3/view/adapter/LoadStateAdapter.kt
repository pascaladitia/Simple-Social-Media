package com.pascal.udacodingpagging3.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pascal.udacodingpagging3.databinding.ItemLoadingBinding

class LoadStateAdapter(val retry: ()-> Unit): LoadStateAdapter<com.pascal.udacodingpagging3.view.adapter.LoadStateAdapter.PlayerLoadStateViewHolder>() {

    class PlayerLoadStateViewHolder(var binding: ItemLoadingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.loading.isVisible = loadState is LoadState.Loading
        }
    }

    override fun onBindViewHolder(
        holder: PlayerLoadStateViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PlayerLoadStateViewHolder {
        val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerLoadStateViewHolder(binding)
    }
}