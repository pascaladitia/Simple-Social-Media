package com.pascal.udacodingpagging3.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pascal.udacodingpagging3.R
import com.pascal.udacodingpagging3.model.ResponseListUser
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(
    private val data: List<ResponseListUser?>?,
    private val itemClick: OnClickListener
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.get(position)

        holder.bind(item)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ResponseListUser?) {
            view.itemUser_name.text = item?.name
            view.itemUser_status.text = item?.username

            view.setOnClickListener{
                itemClick.detail(item)
            }
        }
    }

    interface OnClickListener {
        fun detail(item: ResponseListUser?)
    }
}