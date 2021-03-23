package com.pascal.udacodingpagging3.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pascal.udacodingpagging3.databinding.ItemTodoBinding
import com.pascal.udacodingpagging3.model.ResponseListTodo

class TodoAdapter: PagingDataAdapter<ResponseListTodo, TodoAdapter.TodoHolder>(PostDiffUtil()) {

    class PostDiffUtil: DiffUtil.ItemCallback<ResponseListTodo>() {
        override fun areItemsTheSame(oldItem: ResponseListTodo, newItem: ResponseListTodo): Boolean {

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResponseListTodo, newItem: ResponseListTodo): Boolean {

            return oldItem == newItem
        }

    }

    class TodoHolder(var binding: ItemTodoBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ResponseListTodo?) {
            binding.todoTitle.text = item?.title
            binding.todoStatus.text = "status : ${item?.completed.toString()}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoHolder, position: Int) {
        holder.bind(getItem(position))
    }
}